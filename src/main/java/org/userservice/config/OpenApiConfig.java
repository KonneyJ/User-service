package org.userservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User API")
                        .description("API для управления пользователями системы"))
                .externalDocs(new ExternalDocumentation()
                        .description("Полная документация проекта")
                        .url("https://users.com/docs"));
    }
}
