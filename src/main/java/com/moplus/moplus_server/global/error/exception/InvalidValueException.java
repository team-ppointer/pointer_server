package com.moplus.moplus_server.global.error.exception;

public class InvalidValueException extends BusinessException{

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
