package com.moplus.moplus_server.global.error.exception;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccessDeniedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
