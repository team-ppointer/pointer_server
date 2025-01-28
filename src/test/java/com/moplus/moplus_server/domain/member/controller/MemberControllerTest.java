package com.moplus.moplus_server.domain.member.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.moplus.moplus_server.global.properties.jwt.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2test")
@Sql({"/auth-test-data.sql"})
class MemberControllerTest {

    @Nested
    class 어드민_로그인 {

        @Autowired
        private JwtProperties jwtProperties;

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private WebApplicationContext context;

        private String validToken;
        private Key key;

        @BeforeEach
        public void setMockMvc() throws Exception {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                    .apply(springSecurity()).build();

            key = Keys.hmacShaKeyFor(jwtProperties.accessTokenSecret().getBytes());
            Date issuedAt = new Date();  // 3 hour ago
            Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.accessTokenExpirationMilliTime());
            validToken = Jwts.builder()
                    .setIssuer(jwtProperties.issuer())
                    .setSubject("1")
                    .claim("role", "ROLE_USER")
                    .setIssuedAt(issuedAt)
                    .setExpiration(expiredAt)
                    .signWith(key)
                    .compact();
        }

        @Test
        void 성공() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/member/me")
                            .contentType("application/json")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
                    .andExpect(status().isOk()) // 200 응답 확인
                    .andExpect(jsonPath("$.id").exists()) // MemberGetResponse의 필드 확인
                    .andExpect(jsonPath("$.name").exists())
                    .andExpect(jsonPath("$.email").exists());
        }
    }
}