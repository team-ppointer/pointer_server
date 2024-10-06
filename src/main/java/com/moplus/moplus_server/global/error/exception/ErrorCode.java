package com.moplus.moplus_server.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류, 관리자에게 문의하세요"),

    //모의고사
    PRACTICE_TEST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 모의고사를 찾을 수 없습니다"),

    //문제
    PROBLEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 문제를 찾을 수 없습니다"),

    //시험결과
    TEST_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 시험 결과지를 찾을 수 없습니다"),
    INVALID_SCORE(HttpStatus.CONFLICT, "점수 결과는 0점이하로 내려갈 수 없습니다"),
    ;


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
