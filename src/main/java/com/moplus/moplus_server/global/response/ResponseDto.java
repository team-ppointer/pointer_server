package com.moplus.moplus_server.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moplus.moplus_server.global.error.ErrorResponse;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDto<T>(
        T data,
        String message,
        HttpStatus status
) {
    public static <T> ResponseDto<T> success(final T data) {
        return new ResponseDto<>(data, null, null);
    }

    public static <T> ResponseDto<T> fail(ErrorResponse errorResponse) {
        return new ResponseDto<>(null, errorResponse.getMessage(), errorResponse.getStatus());
    }
}