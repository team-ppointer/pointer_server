package com.moplus.moplus_server.global.error.exception;

public class AlreadyExistException extends BusinessException {
    public AlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
