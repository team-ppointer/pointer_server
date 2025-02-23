package com.moplus.moplus_server.global.response;

import com.moplus.moplus_server.global.error.ErrorResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public record FailResponseDto(
        @NotNull
        String message,
        @NotNull
        HttpStatus status
) {
    public static FailResponseDto fail(ErrorResponse errorResponse) {
        return new FailResponseDto(errorResponse.getMessage(), errorResponse.getStatus());
    }
}
