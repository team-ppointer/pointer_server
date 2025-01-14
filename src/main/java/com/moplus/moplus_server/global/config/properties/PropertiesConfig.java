package com.moplus.moplus_server.global.config.properties;

import com.moplus.moplus_server.global.properties.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
        JwtProperties.class
})
@Configuration
public class PropertiesConfig {

}
