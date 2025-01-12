package com.sw.lotto.account.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.domain.UserLottoEntity;
import com.sw.lotto.account.repository.AccountRepository;
import com.sw.lotto.account.repository.UserLottoRepository;
import com.sw.lotto.bucketlist.domain.BucketListEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLottoService {

    private final UserLottoRepository userLottoRepository;
    private final AccountRepository accountRepository;

    private AccountEntity getCurrentAccount() {
        String signInId = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findBySignInId(signInId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserLottoEntity saveUserLotto(UserLottoEntity userLottoEntity) {
        AccountEntity account = getCurrentAccount();
        userLottoEntity.setAccount(account);
        return userLottoRepository.save(userLottoEntity);
    }

    public List<UserLottoEntity> getUserLottoByAccount() {
        AccountEntity account = getCurrentAccount();
        List<UserLottoEntity> userLottoList = userLottoRepository.findAllByAccount(account);
//        if (userLottoList.isEmpty()) {
//            throw new RuntimeException("User lotto data not found");
//        }
        return userLottoList;
    }

    public Optional<UserLottoEntity> getUserLottoByRound(Integer round) {
        AccountEntity account = getCurrentAccount();
        // 특정 회차를 조회하는 메서드
        return userLottoRepository.findByAccountAndRound(account, round);
    }

}
