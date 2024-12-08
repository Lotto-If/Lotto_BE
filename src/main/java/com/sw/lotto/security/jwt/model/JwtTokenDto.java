package com.sw.lotto.security.jwt.model;

import com.sw.lotto.security.jwt.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenDto {
    private String grantType;
    private String accessToken;

    public static JwtTokenDto from(String accessToken) {
        return JwtTokenDto.builder()
                .grantType(JwtTokenUtil.TOKEN_PREFIX)
                .accessToken(accessToken)
                .build();
    }
}