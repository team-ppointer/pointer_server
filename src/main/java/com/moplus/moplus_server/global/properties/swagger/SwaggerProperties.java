package com.moplus.moplus_server.global.properties.swagger;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swagger")
public record SwaggerProperties(
        List<ServerProperties> servers
) {
    public static record ServerProperties(
            String url,
            String description
    ) {
    }
}
