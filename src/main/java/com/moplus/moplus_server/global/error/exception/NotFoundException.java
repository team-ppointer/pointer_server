package com.moplus.moplus_server.global.error.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode, String message) {
        super(message, errorCode);
    }

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
