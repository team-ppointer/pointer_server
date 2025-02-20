package com.moplus.moplus_server.global.response;

import jakarta.validation.constraints.NotNull;

public record IdResponse(
        @NotNull(message = "ID는 필수입니다")
        Long id
) {
}
