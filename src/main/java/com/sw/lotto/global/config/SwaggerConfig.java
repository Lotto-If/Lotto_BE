package com.sw.lotto.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import io.swagger.v3.oas.models.OpenAPI;
//
//@SecurityScheme(
//        name = "bearerAuth",
//        type = SecuritySchemeType.HTTP,  // HTTP 타입 사용
//        scheme = "bearer",
//        bearerFormat = "JWT"  // JWT 사용
//)
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public OpenAPI openAPI(){
//        SecurityScheme securityScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
//                .in(SecurityScheme.In.HEADER).name("Authorization");
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
//
//        return new OpenAPI()
//                .components(new Components())
//                .info(apiInfo());
//    }
//
//
//
//    private Info apiInfo(){
//        return new Info()
//                .title("Springboot API Docs")
//                .description("API Docs of Lotto Project")
//                .version("v1");
//    }
//
//}

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Security Scheme 정의
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Security Requirement 정의
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

        return new OpenAPI()
                .info(new Info().title("Lotto API")
                        .description("Lotto Application API Documentation")
                        .version("v1"))
                .addSecurityItem(securityRequirement)  // Security Requirement 추가
                .schemaRequirement("BearerAuth", securityScheme);  // Security Scheme 추가
    }
}