package com.moplus.moplus_server.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moplus.moplus_server.domain.auth.dto.request.AdminLoginRequest;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class EmailPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public EmailPasswordAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        final UsernamePasswordAuthenticationToken authRequest;
        try {
            final AdminLoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(),
                    AdminLoginRequest.class);
            authRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(),
                    loginRequest.password());
        } catch (IOException exception) {
            throw new NotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
