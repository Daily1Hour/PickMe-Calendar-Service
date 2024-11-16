package com.example.calendar.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 이 클래스가 스프링의 설정 파일임을 명시하는 어노테이션
public class SwaggerConfig {

    // Swagger API 설정을 위한 Bean을 생성하는 메서드
    @Bean
    public OpenAPI customOpenAPI(){

        // JWT를 사용할 수 있도록 Swagger 보안 설정 구성
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP) // 보안 방식으로 HTTP를 사용
                .scheme("bearer") // JWT 토큰 인증 방식인 "bearer" 방식 사용
                .bearerFormat("JWT") // JWT 형식 사용
                .in(SecurityScheme.In.HEADER) // JWT 토큰을 HTTP 헤더에 포함시키도록 설정
                .name("Authorization"); // Authorization 헤더를 사용하도록 이름 지정

        // 보안 요구사항 설정: Swagger UI가 Authorization 헤더를 포함한 JWT 인증을 요구
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

        // OpenAPI 설정을 반환
        return new OpenAPI()
                .info(new Info().title("PickMe-Calendar") // API 제목 설정
                        .version("v1")) // API 버전 설정
                .addSecurityItem(securityRequirement) // 보안 요구사항을 추가
                .schemaRequirement("BearerAuth", securityScheme); // 보안 요구사항에 대한 설명 추가
    }
}
