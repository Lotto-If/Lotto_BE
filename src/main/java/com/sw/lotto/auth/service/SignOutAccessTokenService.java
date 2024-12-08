package com.sw.lotto.auth.service;

import com.sw.lotto.auth.domain.SignOutAccessToken;
import com.sw.lotto.auth.repository.SignOutAccessTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignOutAccessTokenService {
    private final SignOutAccessTokenRepository signOutAccessTokenRepository;

    public void saveSignOutAccessToken(SignOutAccessToken signOutAccessToken) {
        signOutAccessTokenRepository.save(signOutAccessToken);
    }

    public boolean existsSignOutAccessTokenById(String token) {
        return signOutAccessTokenRepository.existsById(token);
    }
}
