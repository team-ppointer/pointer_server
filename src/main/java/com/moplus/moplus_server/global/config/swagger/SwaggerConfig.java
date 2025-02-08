package com.moplus.moplus_server.global.config.swagger;

import com.moplus.moplus_server.global.properties.swagger.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final SwaggerProperties swaggerProperties;

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("Bearer");
    }

    private List<Server> addServerUrl() {
        return swaggerProperties.servers().stream()
                .map(serverProp -> new Server()
                        .url(serverProp.url())
                        .description(serverProp.description()))
                .collect(Collectors.toList());
    }

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components().addSecuritySchemes("JWT", createAPIKeyScheme()))
                .info(new Info().title("모플 API 명세서")
                        .description("모플 API 명세서 입니다")
                        .version("v0.0.1"))
                .servers(addServerUrl());
    }
}
