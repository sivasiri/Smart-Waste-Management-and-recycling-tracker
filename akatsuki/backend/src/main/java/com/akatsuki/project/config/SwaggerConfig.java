package com.akatsuki.project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Define the security scheme for Bearer token authentication
        SecurityScheme securityScheme = new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        // Define the SecurityRequirement with the security scheme
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");

        // Configure the OpenAPI bean with the security requirement and security scheme
        return new OpenAPI()
                .info(new Info().title("GeoKiks Apis").version("1.0"))
                .addSecurityItem(securityRequirement)  // Add the security requirement globally
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", securityScheme));  // Register the security scheme
    }
}
