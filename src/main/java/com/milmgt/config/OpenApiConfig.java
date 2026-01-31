package com.milmgt.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI militaryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Military Asset Management API")
                        .description("Secure API for managing assets, transfers, and assignments.")
                        .version("1.0"));
    }
}