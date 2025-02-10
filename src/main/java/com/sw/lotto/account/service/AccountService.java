package com.sw.lotto.account.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.model.AccountDto;
import com.sw.lotto.account.repository.AccountRepository;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.sw.lotto.global.exception.ExceptionCode.NON_EXISTENT_SIGNINID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountEntity findAccountBySignInId(String signInId) {
        return accountRepository.findBySignInId(signInId)
                .orElseThrow(() -> new AppException(NON_EXISTENT_SIGNINID));
    }

    public Boolean existsBySignInId(String signInId) {
        return accountRepository.existsBySignInId(signInId);
    }

    public AccountEntity saveAccount(AccountEntity accountEntity) {
        return accountRepository.save(accountEntity);
    }

    public void updateLastSignInAt(AccountEntity accountEntity) {
        accountEntity.setLastSignInAt(LocalDateTime.now());
        accountRepository.save(accountEntity);
    }

    public AccountEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ExceptionCode.UNAUTHORIZED_USER);
        }

        String signInId = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findBySignInId(signInId)
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_USER));
    }
}
