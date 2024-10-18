package com.moplus.moplus_server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(servers = {@Server(url = "https://dev.mopl.kr", description = "Default Server URL")})
@SpringBootApplication
@EnableJpaAuditing
public class MoplusServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoplusServerApplication.class, args);
    }

}
