package com.chaegangjo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Splitty API",
                version = "v1.0.0",
                description = "Splitty API 명세서입니다."
        ),
        servers = {
                @Server(url = "https://splitty.store", description = "Production Server"),
                @Server(url = "http://localhost:8080", description = "Local Server")
        }
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Components components = new Components();

        return new OpenAPI()
                .components(components);
    }
}