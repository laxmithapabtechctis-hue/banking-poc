package com.example.banking_poc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bankingOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Banking Customer Account Management API")
                        .description(
                                "REST API for managing banking customers, " +
                                        "including customer creation, retrieval, " +
                                        "search, update, and deletion."
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Banking POC Development Team")
                                .email("support@example.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                );
    }
}