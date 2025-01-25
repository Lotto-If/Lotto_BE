package com.sw.lotto.global.common.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.repository.AccountRepository;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final AccountRepository accountRepository;

    public AccountEntity getCurrentUser() {
        String signInId = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findBySignInId(signInId)
                .orElseThrow(() -> new AppException(ExceptionCode.NON_EXISTENT_USER));
    }
}