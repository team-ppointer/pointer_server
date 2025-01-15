package com.moplus.moplus_server.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류, 관리자에게 문의하세요"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력 값입니다"),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "잘못된 인증 정보입니다"),

    //모의고사
    PRACTICE_TEST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 모의고사를 찾을 수 없습니다"),

    //문제
    PROBLEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 문제를 찾을 수 없습니다"),

    //시험결과
    TEST_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 시험 결과지를 찾을 수 없습니다"),
    INVALID_SCORE(HttpStatus.CONFLICT, "점수 결과는 0점이하로 내려갈 수 없습니다"),

    //등급 테이블
    RATING_TABLE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 등급 테이블을 찾을 수 없습니다"),

    //이미지
    IMAGE_FILE_EXTENSION_NOT_FOUND(HttpStatus.NOT_FOUND, "지원하지 않는 이미지 확장자입니다"),
    IMAGE_FILE_NOT_FOUND_IN_S3(HttpStatus.NOT_FOUND, "S3에 해당 이미지 파일을 찾을 수 없습니다"),

    //회원
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다"),
    ;


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
