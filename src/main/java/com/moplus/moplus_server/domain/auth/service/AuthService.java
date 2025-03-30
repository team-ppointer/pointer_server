package com.moplus.moplus_server.domain.auth.service;

import com.moplus.moplus_server.domain.auth.dto.response.LoginResponse;
import com.moplus.moplus_server.domain.auth.dto.response.TokenResponse;
import com.moplus.moplus_server.domain.auth.dto.response.oauth.OauthUserInfoResponse;
import com.moplus.moplus_server.domain.auth.service.kakao.KakaoClient;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import com.moplus.moplus_server.global.security.exception.JwtInvalidException;
import com.moplus.moplus_server.global.security.utils.JwtUtil;
import com.moplus.moplus_server.member.domain.Member;
import com.moplus.moplus_server.member.domain.OauthInfo;
import com.moplus.moplus_server.member.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoClient kakaoClient;
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Transactional
    public LoginResponse socialLogin(String socialAccessToken, String provider) {
        OauthUserInfoResponse oauthUserInfoResponse = getOauthUserInfo(socialAccessToken, OAuthProvider.from(provider));
        OauthInfo oauthInfo = oauthUserInfoResponse.toEntity();

        Member member = memberService.getMemberByOAuthInfo(oauthInfo);

        String newAccessToken = jwtUtil.generateAccessToken(member);
        String newRefreshToken = jwtUtil.generateRefreshToken(member);

        return LoginResponse.of(member, new TokenResponse(newAccessToken, newRefreshToken));
    }

    private OauthUserInfoResponse getOauthUserInfo(String socialAccessToken, OAuthProvider provider) {
        return switch (provider) {
            case APPLE -> null; //TODO: Apple OAuth
            case KAKAO -> kakaoClient.getOauthUserInfo(socialAccessToken);
        };
    }

    @Transactional
    public TokenResponse reissueToken(String refreshToken) {
        if (refreshToken == null) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
        }

        Claims claims = getClaims(refreshToken);
        final Member member = getMemberById(claims.getSubject());

        // 새로운 액세스 토큰과 리프레시 토큰 생성
        String newAccessToken = jwtUtil.generateAccessToken(member);
        String newRefreshToken = jwtUtil.generateRefreshToken(member);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    private Claims getClaims(String refreshToken) {
        Claims claims;
        try {
            claims = jwtUtil.getRefreshTokenClaims(refreshToken);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtInvalidException(ErrorCode.EXPIRED_TOKEN.getMessage());
        } catch (SignatureException signatureException) {
            throw new JwtInvalidException(ErrorCode.WRONG_TYPE_TOKEN.getMessage());
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtInvalidException(ErrorCode.UNSUPPORTED_TOKEN.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtInvalidException(ErrorCode.UNKNOWN_ERROR.getMessage());
        }
        return claims;
    }

    private Member getMemberById(String id) {
        try {
            return memberService.getMemberById(Long.parseLong(id));
        } catch (Exception e) {
            throw new BadCredentialsException(ErrorCode.BAD_CREDENTIALS.getMessage());
        }
    }
} 