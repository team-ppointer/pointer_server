package com.moplus.moplus_server;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoplusServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoplusServerApplication.class, args);
    }

}
