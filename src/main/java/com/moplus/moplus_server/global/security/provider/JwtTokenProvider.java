package com.moplus.moplus_server.global.security.provider;

import com.moplus.moplus_server.member.domain.Member;
import com.moplus.moplus_server.member.service.MemberService;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.security.exception.JwtInvalidException;
import com.moplus.moplus_server.global.security.token.JwtAuthenticationToken;
import com.moplus.moplus_server.global.security.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Claims claims = getClaims(authentication);
        final Member member = getMemberById(claims.getSubject());

        return new JwtAuthenticationToken(
                member,
                "",
                List.of(new SimpleGrantedAuthority(member.getRole().getValue())
                ));
    }

    private Claims getClaims(Authentication authentication) {
        Claims claims;
        try {
            claims = jwtUtil.getAccessTokenClaims(authentication);
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

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
