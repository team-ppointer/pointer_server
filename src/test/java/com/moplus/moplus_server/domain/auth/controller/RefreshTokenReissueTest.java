package com.moplus.moplus_server.domain.auth.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.moplus.moplus_server.global.properties.jwt.JwtProperties;
import com.moplus.moplus_server.global.security.utils.CookieUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import java.security.Key;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2test")
@Sql({"/auth-test-data.sql"})
public class RefreshTokenReissueTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private CookieUtil cookieUtil;

    private String validRefreshToken;

    @BeforeEach
    public void setup() {

        // Generate a test token
        Key key = Keys.hmacShaKeyFor(jwtProperties.refreshTokenSecret().getBytes());
        Date issuedAt = new Date();  // 3 hour ago
        Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.refreshTokenExpirationMilliTime());
        validRefreshToken = Jwts.builder()
                .setIssuer(jwtProperties.issuer())
                .setSubject("1")
                .claim("role", "ROLE_USER")
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(key)
                .compact();
    }

    @Nested
    class 토큰_재발급 {

        @Test
        void 성공() throws Exception {
            // given
            Cookie refreshTokenCookie = cookieUtil.createCookie(validRefreshToken);

            // when & then
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/reissue")
                            .cookie(refreshTokenCookie))
                    .andExpect(status().isOk())
                    .andExpect(cookie().exists("refreshToken"))
                    .andExpect(cookie().httpOnly("refreshToken", true))
                    .andExpect(cookie().secure("refreshToken", true))
                    .andExpect(cookie().path("refreshToken", "/"))
                    .andExpect(cookie().attribute("refreshToken", "SameSite", "None"));
        }

        @Test
        void 실패_리프레시토큰_없음() throws Exception {
            // when & then
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/reissue"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void 실패_유효하지_않은_리프레시토큰() throws Exception {
            // given
            Cookie invalidRefreshTokenCookie = new Cookie("refreshToken", "invalid_refresh_token");

            // when & then
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/reissue")
                            .cookie(invalidRefreshTokenCookie))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void 실패_만료된_리프레시토큰() throws Exception {
            // given
            Cookie expiredRefreshTokenCookie = new Cookie("refreshToken", "expired_refresh_token");

            // when & then
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/reissue")
                            .cookie(expiredRefreshTokenCookie))
                    .andExpect(status().isUnauthorized());
        }
    }
}
