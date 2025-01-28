package com.moplus.moplus_server.global.security.handler;

import com.moplus.moplus_server.domain.member.domain.Member;
import com.moplus.moplus_server.global.security.AuthConstants;
import com.moplus.moplus_server.global.security.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailPasswordSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(member);
        String refreshToken = jwtUtil.generateRefreshToken(member);
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + accessToken);
        response.addHeader(AuthConstants.REFRESH_TOKEN_HEADER, AuthConstants.TOKEN_TYPE + " " + refreshToken);
    }

}
