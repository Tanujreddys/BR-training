package com.example.productservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productServiceOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Product Service API")
                                .version("1.0")
                                .description(
                                        "Professional API documentation for the Product Service. " +
                                                "Includes product management, pagination, sorting, filtering " +
                                                "and API versioning."
                                )
                );
    }
}