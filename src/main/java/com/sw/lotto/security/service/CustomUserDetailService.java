package com.sw.lotto.security.service;

import com.sw.lotto.account.domain.AccountEntity;
import com.sw.lotto.account.service.AccountService;
import com.sw.lotto.auth.model.AccountAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final AccountService accountService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String signInId) throws UsernameNotFoundException {

        // authority 추가
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // admin 권한 할당 추가

        AccountEntity accountEntity = accountService.findAccountBySignInId(signInId);

        //여기서 생성한 객체의 비밀번호를 검증한다.
        AccountAuth userDetails = new AccountAuth(accountEntity.getSignInId(), accountEntity.getPassword(), authorities);
        return userDetails;
    }
}
