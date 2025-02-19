package com.moplus.moplus_server.global.response;

import jakarta.validation.constraints.NotNull;

public record SuccessResponseDto<T>(
        @NotNull
        T data
) {
    public static <T> SuccessResponseDto<T> success(final T data) {
        return new SuccessResponseDto<>(data);
    }
}