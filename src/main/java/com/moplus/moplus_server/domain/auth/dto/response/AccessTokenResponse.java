package com.moplus.moplus_server.domain.auth.dto.response;

import jakarta.validation.constraints.NotNull;

public record AccessTokenResponse(
        @NotNull(message = "accessToken을 입력해주세요.")
        String accessToken
) {
}