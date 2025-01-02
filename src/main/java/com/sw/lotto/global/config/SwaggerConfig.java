package com.sw.lotto.global.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import io.swagger.v3.oas.models.OpenAPI;

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,  // HTTP 타입 사용
        scheme = "bearer",
        bearerFormat = "JWT"  // JWT 사용
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo(){
        return new Info()
                .title("Springboot API Docs")
                .description("API Docs of Lotto Project")
                .version("v1");
    }

}
