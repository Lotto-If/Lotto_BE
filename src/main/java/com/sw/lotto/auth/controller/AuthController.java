package com.sw.lotto.auth.controller;

import com.sw.lotto.auth.model.AccountAuth;
import com.sw.lotto.auth.model.SignInDto;
import com.sw.lotto.auth.model.SignUpDto;
import com.sw.lotto.auth.service.AuthService;
import com.sw.lotto.global.common.controller.CommonController;
import com.sw.lotto.global.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends CommonController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse signUp(@RequestBody SignUpDto signUpDto) {
        final SignUpDto response = authService.signUp(signUpDto);
        return success(response);
    }

    @PostMapping("/signin")
    public ApiResponse signIn(@RequestBody SignInDto signInDto) {
        final AccountAuth response = authService.signIn(signInDto);
        return success(response);
    }

    @PostMapping("/signout")
    public ApiResponse signOut(@RequestHeader("Authorization") String accessToken) {
        authService.signOut(accessToken);
        return success();
    }

    @PostMapping("/reissue")
    public ApiResponse reissue(@CookieValue("RefreshToken") String refreshToken, @RequestHeader("Authorization") String accessToken) {
        final AccountAuth response = authService.reissue(refreshToken, accessToken);
        return success(response);
    }
}
