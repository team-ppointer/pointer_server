package com.moplus.moplus_server.domain.auth.controller;

import com.moplus.moplus_server.domain.auth.dto.request.AdminLoginRequest;
import com.moplus.moplus_server.domain.auth.dto.response.AccessTokenResponse;
import com.moplus.moplus_server.domain.auth.dto.response.LoginResponse;
import com.moplus.moplus_server.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "인증", description = "인증 관련 API")
public interface AuthControllerDocs {

    @SecurityRequirements(value = {})
    @Operation(
            summary = "소셜 로그인",
            description = "소셜 액세스 토큰으로 로그인하여 자체 액세스 토큰을 발급받고 리프레시 토큰을 쿠키에 설정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class)
                            ),
                            headers = {
                                    @Header(
                                            name = "Authorization",
                                            description = "발급된 액세스 토큰",
                                            schema = @Schema(type = "string")
                                    ),
                                    @Header(
                                            name = "Set-Cookie",
                                            description = "리프레시 토큰이 담긴 HTTP Only 쿠키",
                                            schema = @Schema(
                                                    type = "string",
                                                    example = "refreshToken=xxx; Path=/; HttpOnly; Secure; SameSite=None"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "유효하지 않은 소셜 액세스 토큰",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    ResponseEntity<LoginResponse> socialLogin(
            String accessToken,
            String provider,
            HttpServletResponse response
    );

    @SecurityRequirements(value = {})
    @Operation(
            summary = "어드민 로그인",
            description = "이메일과 비밀번호로 로그인하여 액세스 토큰을 발급받고 리프레시 토큰을 쿠키에 설정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AccessTokenResponse.class)
                            ),
                            headers = @Header(
                                    name = "Set-Cookie",
                                    description = "리프레시 토큰이 담긴 HTTP Only 쿠키",
                                    schema = @Schema(
                                            type = "string",
                                            example = "refreshToken=xxx; Path=/; HttpOnly; Secure; SameSite=None"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패 (잘못된 이메일 또는 비밀번호)",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    ResponseEntity<AccessTokenResponse> adminLogin(AdminLoginRequest request);

    @SecurityRequirements(value = {})
    @Operation(
            summary = "토큰 재발급",
            description = "리프레시 토큰을 통해 새로운 액세스 토큰을 발급하고 새로운 리프레시 토큰을 쿠키에 설정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "토큰 재발급 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AccessTokenResponse.class)
                            ),
                            headers = @Header(
                                    name = "Set-Cookie",
                                    description = "새로운 리프레시 토큰이 담긴 HTTP Only 쿠키",
                                    schema = @Schema(
                                            type = "string",
                                            example = "refreshToken=xxx; Path=/; HttpOnly; Secure; SameSite=None"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "유효하지 않은 리프레시 토큰",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "리프레시 토큰 쿠키 없음",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    ResponseEntity<AccessTokenResponse> reissueToken(HttpServletRequest request, HttpServletResponse response);
} 