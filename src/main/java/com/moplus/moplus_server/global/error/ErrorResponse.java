package com.moplus.moplus_server.global.error;

import com.moplus.moplus_server.global.error.exception.ErrorCode;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    @NotNull
    private String message;
    @NotNull
    private HttpStatus status;

    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
    }

    private ErrorResponse(final String message, final HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public static ErrorResponse from(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse from(final String message, final HttpStatus status) {
        return new ErrorResponse(message, status);
    }
}
