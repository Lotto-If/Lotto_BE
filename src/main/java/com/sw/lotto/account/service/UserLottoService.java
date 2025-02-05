package com.sw.lotto.account.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.domain.UserLottoEntity;
import com.sw.lotto.account.model.LottoResult;
import com.sw.lotto.account.model.UserLottoRequestDto;
import com.sw.lotto.account.model.UserLottoResponseDto;
import com.sw.lotto.account.repository.UserLottoRepository;
import com.sw.lotto.es.lotto.dto.LottoResponseDto;
import com.sw.lotto.es.lotto.service.LottoService;
import com.sw.lotto.global.common.service.CurrentUserService;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.global.exception.ExceptionCode;
import com.sw.lotto.mail.MailLogEntity;
import com.sw.lotto.mail.MailLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLottoService {

    private final UserLottoRepository userLottoRepository;
    private final MailLogService mailLogService;
    private final LottoService lottoService;
    private final CurrentUserService currentUserService;

    public UserLottoResponseDto saveUserLotto(UserLottoRequestDto userLottoRequestDto) {
        AccountEntity account = currentUserService.getCurrentUser();
        Integer latestRound = lottoService.getLatestLotto().getRound();

        if (userLottoRequestDto.getRound() < latestRound) {
            throw new AppException(ExceptionCode.INVALID_LOTTO_ROUND);}

        if (!isValidPredictedNumbers(userLottoRequestDto.getPredictedNumbers())) {
            throw new AppException(ExceptionCode.INVALID_PREDICTED_NUMBERS);}

        UserLottoEntity entity = userLottoRequestDto.toUserLottoEntity(account);
        UserLottoEntity savedEntity = userLottoRepository.save(entity);
        return UserLottoResponseDto.fromUserLottoEntity(savedEntity);
    }

    private boolean isValidPredictedNumbers(String predictedNumbers) {
        try {
            List<Integer> numbers = Arrays.stream(predictedNumbers.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
            return numbers.size() == 6 && numbers.stream().allMatch(n -> n >= 1 && n <= 45);
        } catch (Exception e) {
            return false;
        }
    }

    public List<UserLottoResponseDto> getUserLottoByAccount() {
        AccountEntity account = currentUserService.getCurrentUser();
        List<UserLottoEntity> userLottoList = userLottoRepository.findAllByAccount(account);
        return userLottoList.stream().map(UserLottoResponseDto::fromUserLottoEntity).toList();
    }

    public UserLottoResponseDto getUserLottoByRound(Integer round) {
        AccountEntity account = currentUserService.getCurrentUser();

        UserLottoEntity userLottoEntity = userLottoRepository.findByAccountAndRound(account, round)
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_LOTTO));

        return UserLottoResponseDto.fromUserLottoEntity(userLottoEntity);
    }

    public void removeUserLotto(Long userLottoOid) {
        UserLottoEntity existingEntity = userLottoRepository.findByUserLottoOid(userLottoOid)
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_LOTTO));
        userLottoRepository.delete(existingEntity);
    }

    public UserLottoResponseDto updateUserLotto(Long userLottoOid, String predictedNumbers) {
        UserLottoEntity existingEntity = userLottoRepository.findByUserLottoOid(userLottoOid)
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_LOTTO));

        if (!isValidPredictedNumbers(predictedNumbers)) {
            throw new AppException(ExceptionCode.INVALID_PREDICTED_NUMBERS);}

        UserLottoEntity updatedEntity = userLottoRepository.save(existingEntity);
        return UserLottoResponseDto.fromUserLottoEntity(updatedEntity);
    }

    // @Scheduled(cron = "0 0 22 * * SAT", zone = "Asia/Seoul")
    @Transactional
    public void checkWinningsAndNotify() {
        LottoResponseDto lottoInfo = lottoService.getLatestLotto();
        Integer latestRound = lottoInfo.getRound();

        LottoResult lottoResult = parseLottoNumbers(lottoInfo);
        List<UserLottoEntity> userLottos = userLottoRepository.findAllByRound(latestRound);
        processUserLottos(userLottos, lottoResult);
        notifyUsers(userLottos,lottoInfo);
    }

    private LottoResult parseLottoNumbers(LottoResponseDto lottoInfo) {
        List<Integer> lottoNumbers = Arrays.stream(lottoInfo.getFinalNumbers().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        Integer bonusNumber = lottoNumbers.get(lottoNumbers.size() - 1);
        List<Integer> mainNumbers = lottoNumbers.subList(0, lottoNumbers.size() - 1);

        return new LottoResult(mainNumbers, bonusNumber);
    }

    private void processUserLottos(List<UserLottoEntity> userLottos, LottoResult lottoResult) {
        for (UserLottoEntity userLotto : userLottos) {
            List<Integer> predictedNumbers = Arrays.stream(userLotto.getPredictedNumbers().split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();

            long matchCount = predictedNumbers.stream().filter(lottoResult.getMainNumbers()::contains).count();
            boolean hasBonusNumber = predictedNumbers.contains(lottoResult.getBonusNumber());

            String prizeRank = determinePrizeRank(matchCount, hasBonusNumber);
            userLotto.setCorrectCount((int) matchCount);
            userLotto.setPrizeRank(prizeRank);
            userLottoRepository.save(userLotto);
        }
    }

    private void notifyUsers(List<UserLottoEntity> userLottos, LottoResponseDto lottoResponseDto) {
        for (UserLottoEntity userLotto : userLottos) {
            if (Boolean.TRUE.equals(userLotto.getNotification())) {
                MailLogEntity email = mailLogService.preparePrizeNotificationEmail(userLotto, lottoResponseDto.getFinalNumbers());
                mailLogService.sendEmail(email);
            }
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

