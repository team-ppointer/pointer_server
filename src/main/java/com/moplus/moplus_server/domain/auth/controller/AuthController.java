package com.moplus.moplus_server.domain.auth.controller;

import com.moplus.moplus_server.domain.auth.dto.request.AdminLoginRequest;
import com.moplus.moplus_server.domain.auth.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Operation(summary = "어드민 로그인", description = "아아디 패스워드 로그인 후 토큰 발급합니다.")
    @PostMapping("/admin/login")
    public ResponseEntity<TokenResponse> adminLogin(
            @RequestBody AdminLoginRequest request
    ) {
        // 실제 처리는 Security 필터에서 이루어지며, 이 메서드는 Swagger 명세용입니다.
        return null;
    }

}
