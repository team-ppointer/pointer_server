package com.moplus.moplus_server.domain.auth.controller;

import com.moplus.moplus_server.domain.auth.dto.request.AdminLoginRequest;
import com.moplus.moplus_server.domain.auth.dto.response.AccessTokenResponse;
import com.moplus.moplus_server.domain.auth.dto.response.TokenResponse;
import com.moplus.moplus_server.domain.auth.service.AuthService;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import com.moplus.moplus_server.global.security.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    private static String validateRefreshTokenCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new NotFoundException(ErrorCode.BLANK_INPUT_VALUE);
        }
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.BLANK_INPUT_VALUE));
    }

    @Override
    @PostMapping("/admin/login")
    public ResponseEntity<AccessTokenResponse> adminLogin(
            @Valid @RequestBody AdminLoginRequest request
    ) {
        // 실제 처리는 Security 필터에서 이루어지며, 이 메서드는 Swagger 명세용입니다.
        return null;
    }

    @Override
    @GetMapping("/reissue")
    public ResponseEntity<AccessTokenResponse> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = validateRefreshTokenCookie(request);

        TokenResponse tokenResponse = authService.reissueToken(refreshToken);

        response.addCookie(cookieUtil.createCookie(tokenResponse.refreshToken()));

        return ResponseEntity.ok(new AccessTokenResponse(tokenResponse.accessToken()));
    }
}
