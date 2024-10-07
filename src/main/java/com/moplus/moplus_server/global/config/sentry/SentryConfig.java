package com.moplus.moplus_server.global.config.sentry;

import io.sentry.Sentry;
import io.sentry.SentryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SentryConfig {

    @Value("${sentry.dsn}")
    private String dsn;

    @Bean
    public Sentry.OptionsConfiguration<SentryOptions> sentryOptions() {
        return options -> {
            options.setDsn(dsn);
            options.setSampleRate(1.0); // 모든 이벤트를 100% 수집
            options.setDebug(true); // 디버그 모드 활성화
            options.setBeforeSend((event, hint) -> {
                return event;
            });
        };
    }
}
