package com.moplus.moplus_server.global.security.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.moplus.moplus_server.global.properties.jwt.JwtProperties;
import com.moplus.moplus_server.global.security.token.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private JwtUtil jwtUtil;

    private String validToken;
    private Key key;

    @BeforeEach
    public void setup() {
        // Mock JwtProperties
        when(jwtProperties.issuer()).thenReturn("testIssuer");
        when(jwtProperties.accessTokenSecret()).thenReturn(
                "mySecretKeymySecretKeymySecretKeymySecretKey"); // 256-bit key
        when(jwtProperties.accessTokenExpirationMilliTime()).thenReturn(7200000L); // 1 hour

        // Generate a test token
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
    public void 유효한_토큰_통과() {

        Authentication jwtAuthenticationToken = new JwtAuthenticationToken(validToken);

        // Act
        Claims claimsJws = jwtUtil.getAccessTokenClaims(jwtAuthenticationToken);

        // Assert
        assertNotNull(claimsJws);
        assertEquals("testIssuer", claimsJws.getIssuer());
        assertEquals("1", claimsJws.getSubject());
        assertEquals("ROLE_USER", claimsJws.get("role", String.class));
    }

    @Test
    public void 만료된_토큰_예외() {
        Date issuedAt = new Date(System.currentTimeMillis() - 10800000L);  // 3 hour ago
        Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.accessTokenExpirationMilliTime());
        String expiredToken = Jwts.builder()
                .setIssuer(jwtProperties.issuer())
                .setSubject("12345")
                .claim("role", "ROLE_USER")
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt) // 1 hour ago
                .signWith(key)
                .compact();

        Authentication jwtAuthenticationToken = new JwtAuthenticationToken(expiredToken);

        assertThrows(ExpiredJwtException.class,
                () -> jwtUtil.getAccessTokenClaims(jwtAuthenticationToken));
    }

    @Test
    public void 조작된_토큰_예외() {
        //토큰 변형
        String invalidSignatureToken = validToken.substring(0, validToken.length() - 1) + "@";
        Authentication jwtAuthenticationToken = new JwtAuthenticationToken(invalidSignatureToken);
        assertThrows(SignatureException.class, () -> {
            jwtUtil.getAccessTokenClaims(jwtAuthenticationToken);
        });
    }

    @Test
    public void jwt_토큰_형식이_아닌_토큰_예외() {
        //형식이 jwt 토큰 형식 조차 아닌 토큰
        String malformedToken = "malformed.token.here";
        Authentication jwtAuthenticationToken = new JwtAuthenticationToken(malformedToken);
        assertThrows(MalformedJwtException.class, () -> {
            jwtUtil.getAccessTokenClaims(jwtAuthenticationToken);
        });
    }
}
