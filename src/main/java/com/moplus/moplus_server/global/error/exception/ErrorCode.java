package com.moplus.moplus_server.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류, 관리자에게 문의하세요"),

    //모의고사
    PRACTICE_TEST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 모의고사를 찾을 수 없습니다"),
    ;


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
