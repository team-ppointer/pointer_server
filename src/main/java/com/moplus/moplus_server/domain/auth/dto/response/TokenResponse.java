package com.moplus.moplus_server.domain.auth.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
