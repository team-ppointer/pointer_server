package com.moplus.moplus_server;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoplusServerApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(MoplusServerApplication.class, args);
        Environment environment = applicationContext.getEnvironment();

        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            System.out.println("No active profiles are set.");
        } else {
            System.out.println("Active profiles: " + Arrays.toString(activeProfiles));
        }
    }


}
