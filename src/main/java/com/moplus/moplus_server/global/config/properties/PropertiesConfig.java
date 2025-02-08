package com.moplus.moplus_server.global.config.properties;

import com.moplus.moplus_server.global.properties.jwt.JwtProperties;
import com.moplus.moplus_server.global.properties.swagger.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
        JwtProperties.class,
        SwaggerProperties.class
})
@Configuration
public class PropertiesConfig {

}
