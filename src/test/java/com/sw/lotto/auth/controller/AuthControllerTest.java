package com.sw.lotto.auth.controller;

import com.sw.lotto.account.service.AccountService;
import com.sw.lotto.auth.model.AccountAuth;
import com.sw.lotto.auth.model.SignInDto;
import com.sw.lotto.auth.model.SignUpDto;
import com.sw.lotto.auth.service.AuthService;
import com.sw.lotto.auth.service.RefreshTokenService;
import com.sw.lotto.security.filter.JwtAuthenticationFilter;
import com.sw.lotto.security.jwt.util.JwtTokenUtil;
import com.sw.lotto.security.jwt.util.TokenManager;
import com.sw.lotto.security.service.CustomAuthenticationProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenManager tokenManager;
    @MockBean
    private AccountService accountService;
    @MockBean
    private CustomAuthenticationProvider authenticationManager;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private RefreshTokenService refreshTokenService;

    private SignUpDto signUpDto;
    private SignInDto signInDto;
    private AccountAuth accountAuth;

    @BeforeEach
    void setUp() {
        signUpDto = SignUpDto.builder()
                .signInId("signInId")
                .password("password")
                .email("email@example.com")
                .build();
        signInDto = SignInDto.builder()
                .signInId("signInId")
                .password("password")
                .build();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        accountAuth = new AccountAuth("signInId", authorities);
        accountAuth.setAccessToken("accessToken");
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUp_success() throws Exception {
        when(authService.signUp(any(SignUpDto.class))).thenReturn(signUpDto);

        mvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"signInId\":\"signInId\",\"password\":\"password\",\"email\":\"email@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.signInId").value("signInId"));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void signIn_success() throws Exception {

        // 로그인 테스트 수행
        when(authService.signIn(any(SignInDto.class))).thenReturn(accountAuth);

        mvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"signInId\":\"signInId\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value("accessToken"));
    }

    @Test
    @DisplayName("인가된 사용자 접근 테스트")
    @WithMockUser(username = "testId", roles = {"USER"})
    void authorizedUserAccess() throws Exception {
        mvc.perform(post("/api/auth/signout")
                        .header("Authorization", "Bearer accessToken"))
                .andExpect(status().isOk());
    }
}