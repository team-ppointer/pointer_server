package com.moplus.moplus_server.global.security;

import com.moplus.moplus_server.domain.member.domain.Member;
import com.moplus.moplus_server.global.properties.jwt.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String TOKEN_ROLE_NAME = "role";
    private final JwtProperties jwtProperties;

    public String generateAccessToken(Member member) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.accessTokenExpirationMilliTime());
        return Jwts.builder()
                .setIssuer(jwtProperties.issuer())
                .setSubject(member.getId().toString())
                .claim(TOKEN_ROLE_NAME, member.getRole().getValue())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getAccessTokenKey())
                .compact();
    }

    public String generateRefreshToken(Member member) {
        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.refreshTokenExpirationMilliTime());

        return Jwts.builder()
                .setIssuer(jwtProperties.issuer())
                .setSubject(member.getId().toString())
                .claim(TOKEN_ROLE_NAME, member.getRole().getValue())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getRefreshTokenKey())
                .compact();
    }

    private Key getAccessTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.accessTokenSecret().getBytes());
    }

    private Key getRefreshTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.refreshTokenSecret().getBytes());
    }
}
