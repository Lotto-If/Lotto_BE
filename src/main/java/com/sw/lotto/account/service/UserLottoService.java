package com.sw.lotto.account.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.domain.UserLottoEntity;
import com.sw.lotto.account.repository.AccountRepository;
import com.sw.lotto.account.repository.UserLottoRepository;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import com.sw.lotto.es.lotto.model.LottoDocument;
import com.sw.lotto.es.lotto.repository.LottoRepository;
import com.sw.lotto.mail.MailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLottoService {

    private final UserLottoRepository userLottoRepository;
    private final AccountRepository accountRepository;
    private final MailService mailService;
    private final LottoRepository lottoRepository;

    private AccountEntity getCurrentAccount() {
        String signInId = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findBySignInId(signInId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserLottoEntity saveUserLotto(UserLottoEntity userLottoEntity) {
        AccountEntity account = getCurrentAccount();
        Integer latestRound = getLatestLottoRound();

        if (userLottoEntity.getRound() < latestRound) {
            throw new RuntimeException("과거 로또 회차는 입력할 수 없습니다.");
        }

        userLottoEntity.setAccount(account);
        return userLottoRepository.save(userLottoEntity);
    }

    public List<UserLottoEntity> getUserLottoByAccount() {
        AccountEntity account = getCurrentAccount();
        List<UserLottoEntity> userLottoList = userLottoRepository.findAllByAccount(account);
        return userLottoList;
    }

    public Optional<UserLottoEntity> getUserLottoByRound(Integer round) {
        AccountEntity account = getCurrentAccount();
        return userLottoRepository.findByAccountAndRound(account, round);
    }

    public Integer getLatestLottoRound() {
        // 엘라스틱서치에서 최신 회차 가져오기
        return lottoRepository.findTopByOrderByRoundDesc()
                .map(LottoDocument::getRound)
                .orElseThrow(() -> new RuntimeException("No lotto data found"));
    }

    // @Scheduled(cron = "0 0 22 * * SAT")
    public void checkWinningsAndNotify() {
        Integer latestRound = getLatestLottoRound();
        List<UserLottoEntity> userLottos = userLottoRepository.findAllByRound(latestRound);

        LottoDocument lottoInfo = lottoRepository.findByRound(latestRound)
                .orElseThrow(() -> new RuntimeException("No lotto data for round: " + latestRound));

        List<Integer> lottoNumbers = Arrays.stream(lottoInfo.getFinalNumbers().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        Integer bonusNumber = lottoNumbers.get(lottoNumbers.size() - 1);
        List<Integer> mainNumbers = lottoNumbers.subList(0, lottoNumbers.size() - 1);

        for (UserLottoEntity userLotto : userLottos) {
            List<Integer> predictedNumbers = Arrays.stream(userLotto.getPredictedNumbers().split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();

            long matchCount = predictedNumbers.stream().filter(mainNumbers::contains).count();
            boolean hasBonusNumber = predictedNumbers.contains(bonusNumber);

            String prizeRank = determinePrizeRank(matchCount, hasBonusNumber);
            userLotto.setCorrectCount((int) matchCount);
            userLotto.setPrizeRank(prizeRank);
            userLottoRepository.save(userLotto);

            mailService.sendPrizeNotificationEmail(userLotto, prizeRank, matchCount);
        }
    }

    private String determinePrizeRank(long matchCount, boolean hasBonusNumber) {
        if (matchCount == 6) {
            return "1등";
        } else if (matchCount == 5 && hasBonusNumber) {
            return "2등";
        } else if (matchCount == 5) {
            return "3등";
        } else if (matchCount == 4) {
            return "4등";
        }else {
            return "낙첨";
        }
    }

}

