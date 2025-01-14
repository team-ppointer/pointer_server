package com.moplus.moplus_server.domain.auth.controller;

import com.moplus.moplus_server.domain.auth.dto.request.AdminLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Operation(summary = "어드민 로그인", description = "아아디 패스워드 로그인 후 토큰 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    headers = {
                            @Header(name = "Authorization", description = "Access Token", schema = @Schema(type = "string")),
                            @Header(name = "RefreshToken", description = "Refresh Token", schema = @Schema(type = "string"))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/admin/login")
    public void adminLogin(
            @RequestBody AdminLoginRequest request
    ) {
        // 실제 처리는 Security 필터에서 이루어지며, 이 메서드는 Swagger 명세용입니다.
    }
}
