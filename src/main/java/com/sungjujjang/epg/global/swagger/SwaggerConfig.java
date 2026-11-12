package com.sungjujjang.epg.global.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        return new OpenAPI()
            .info(new Info()
                .title("API 문서 제목")
                .version("v1.0.0")
                .description("프로젝트 API 명세서입니다.")
            )
            .components(new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme)
            )
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }
}
