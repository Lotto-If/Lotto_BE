package com.sw.lotto.auth.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.service.AccountService;
import com.sw.lotto.auth.domain.RefreshToken;
import com.sw.lotto.auth.domain.SignOutAccessToken;
import com.sw.lotto.auth.model.AccountAuth;
import com.sw.lotto.auth.model.SignInDto;
import com.sw.lotto.auth.model.SignUpDto;
import com.sw.lotto.global.exception.AppException;
import com.sw.lotto.security.jwt.model.JwtTokenDto;
import com.sw.lotto.security.jwt.util.JwtTokenUtil;
import com.sw.lotto.security.jwt.util.TokenManager;
import com.sw.lotto.security.service.CustomAuthenticationProvider;
import com.sw.lotto.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sw.lotto.global.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountService accountService;
    private final CustomAuthenticationProvider authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;
    private final SignOutAccessTokenService signOutAccessTokenService;
    private final CustomUserDetailService customUserDetailService;
    private final TokenManager tokenManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpDto signUp(SignUpDto signUpDto) {
        final String signInId = signUpDto.getSignInId();

        if (accountService.existsBySignInId(signInId)) {
            throw new AppException(ALREADY_EXIST_SIGNINID);
        }

        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        final AccountEntity savedAccountEntity = accountService.saveAccount(AccountEntity.of(signUpDto));

        return SignUpDto.of(savedAccountEntity);
    }

    //signIn
    @Transactional(readOnly = true)
    public AccountAuth signIn(SignInDto signInDto) {

        Authentication authentication;

        try {
            final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(signInDto.getSignInId(),
                    signInDto.getPassword());
            authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException e) {
            throw new AppException(AUTHENTICATION_FAILED);
        }

        final AccountEntity accountEntity = accountService.findAccountBySignInId(signInDto.getSignInId());

        final AccountAuth accountAuth = getAccountAuth(accountEntity, authentication);

        final String accessToken = jwtTokenUtil.generateToken(signInDto.getSignInId(), accountEntity.getObjectId(), authentication.getAuthorities(), JwtTokenUtil.ACCESS_TOKEN_EXPIRE_TIME);
        final RefreshToken refreshToken = refreshTokenService.saveRefreshToken(signInDto.getSignInId(), accountEntity.getObjectId(), authentication.getAuthorities(), JwtTokenUtil.REFRESH_TOKEN_EXPIRE_TIME);
        jwtTokenUtil.setRefreshTokenAtCookie(refreshToken);

        accountAuth.setAccessToken(accessToken);
        accountService.updateLastSignInAt(accountEntity);

        return accountAuth;
    }

    private AccountAuth getAccountAuth(AccountEntity accountEntity, Authentication authentication) {
        Object p = authentication.getPrincipal();
        if (p instanceof AccountAuth) {
            AccountAuth details = (AccountAuth) p;
            details.setProfileImage(accountEntity.getProfileImage());
            return details;
        } else {
            throw new AppException(AUTHENTICATION_FAILED);
        }
    }

    //signOut
    public void signOut(String accessToken) {
        accessToken = jwtTokenUtil.resolveToken(accessToken);
        if (accessToken == null) throw new AppException(INVALID_TOKEN);
        final String signInId = jwtTokenUtil.parseToken(accessToken);
        final long remainTime = jwtTokenUtil.getRemainTime(accessToken);
        refreshTokenService.deleteRefreshTokenById(signInId);
        signOutAccessTokenService.saveSignOutAccessToken(SignOutAccessToken.from(signInId, accessToken, remainTime));
    }

    //reissue
    public AccountAuth reissue(String refreshToken, Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new AppException(NOT_AUTHORIZED_ACCESS);
        }

        final String curSignInId = principal.getName();
        final RefreshToken redisRefreshToken = refreshTokenService.findRefreshTokenById(curSignInId);

        if (refreshToken == null || !refreshToken.equals(redisRefreshToken.getRefreshToken())) {
            throw new AppException(MISMATCH_SIGNINID_TOKEN);
        }
        return createRefreshToken(refreshToken, curSignInId);
    }

    private AccountAuth createRefreshToken(String refreshToken, String signInId) {
        final UserDetails userDetails = customUserDetailService.loadUserByUsername(signInId);
        if (userDetails == null) throw new AppException(AUTHENTICATION_FAILED);

        final String accessToken = jwtTokenUtil.generateToken(signInId, ((AccountAuth) userDetails).getAccountId(), userDetails.getAuthorities(), JwtTokenUtil.ACCESS_TOKEN_EXPIRE_TIME);
        if (accessToken == null) throw new AppException(AUTHENTICATION_FAILED);

        if (lessThanReissueExpirationTimesLeft(refreshToken)) {
            final RefreshToken newRedisToken = refreshTokenService.saveRefreshToken(signInId, ((AccountAuth) userDetails).getAccountId(), userDetails.getAuthorities(), JwtTokenUtil.REFRESH_TOKEN_EXPIRE_TIME);
            if (newRedisToken == null) throw new AppException(AUTHENTICATION_FAILED);
            jwtTokenUtil.setRefreshTokenAtCookie(newRedisToken);
        }

        SecurityContextHolder.clearContext();
        final Authentication authentication = tokenManager.getAuthentication(accessToken);
        // Authentication 객체를 SecurityContext에 저장, 인증 상태로 만듦
        if (tokenManager.validateToken(accessToken)) SecurityContextHolder.getContext().setAuthentication(authentication);

        final AccountEntity accountEntity = accountService.findAccountBySignInId(signInId);
        final AccountAuth accountAuth = getAccountAuth(accountEntity, authentication);
        accountAuth.setAccessToken(accessToken);
        return accountAuth;
    }

    private boolean lessThanReissueExpirationTimesLeft(String refreshToken) {
        return jwtTokenUtil.getRemainTime(refreshToken) < JwtTokenUtil.REISSUE_EXPIRE_TIME;
    }
}
