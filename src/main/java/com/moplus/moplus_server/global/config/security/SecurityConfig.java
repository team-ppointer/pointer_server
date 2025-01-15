package com.moplus.moplus_server.global.config.security;

import com.moplus.moplus_server.domain.member.service.MemberService;
import com.moplus.moplus_server.global.security.filter.EmailPasswordAuthenticationFilter;
import com.moplus.moplus_server.global.security.handler.EmailPasswordSuccessHandler;
import com.moplus.moplus_server.global.security.provider.EmailPasswordAuthenticationProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;
    private final EmailPasswordSuccessHandler emailPasswordSuccessHandler;
    private final AuthenticationConfiguration authenticationConfiguration;

    private String[] allowUrls = {"/", "/favicon.ico", "/swagger-ui/**", "/v3/**"};

    @Value("${cors-allowed-origins}")
    private List<String> corsAllowedOrigins;

    @Bean
    public WebSecurityCustomizer configure() {
        // filter 안타게 무시
        return (web) -> web.ignoring().requestMatchers(allowUrls);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(customizer -> customizer.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(allowUrls).permitAll()
                        .anyRequest().authenticated());

        http
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint((request, response, authException) ->
                                response.setStatus(HttpStatus.UNAUTHORIZED.value()))); // 인증,인가가 되지 않은 요청 시 발생시

        http
                .addFilterAt(emailPasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
//            .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public EmailPasswordAuthenticationFilter emailPasswordAuthenticationFilter() throws Exception {
        EmailPasswordAuthenticationFilter emailPasswordAuthenticationFilter = new EmailPasswordAuthenticationFilter(
                authenticationManager(authenticationConfiguration));
        emailPasswordAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/admin/login");
        emailPasswordAuthenticationFilter.setAuthenticationSuccessHandler(emailPasswordSuccessHandler);
        emailPasswordAuthenticationFilter.afterPropertiesSet();
        return emailPasswordAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        ProviderManager providerManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        providerManager.getProviders().add(emailPasswordAuthenticationProvider());
        return configuration.getAuthenticationManager();
    }

    @Bean
    public EmailPasswordAuthenticationProvider emailPasswordAuthenticationProvider() {
        return new EmailPasswordAuthenticationProvider(memberService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsAllowedOrigins);
        configuration.addAllowedMethod("*");
        configuration.setAllowedHeaders(List.of("*")); // 허용할 헤더
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 적용
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
