package com.moplus.moplus_server.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류, 관리자에게 문의하세요"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력 값입니다"),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "잘못된 인증 정보입니다"),
    BLANK_INPUT_VALUE(HttpStatus.BAD_REQUEST, "빈 값이 입력되었습니다"),

    //Auth
    AUTH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "시큐리티 인증 정보를 찾을 수 없습니다."),
    UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "알 수 없는 에러"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 Token입니다"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 길이 및 형식이 다른 Token입니다"),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "서명이 잘못된 토큰입니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "토큰이 없습니다"),
    TOKEN_SUBJECT_FORMAT_ERROR(HttpStatus.UNAUTHORIZED, "Subject 값에 Long 타입이 아닌 다른 타입이 들어있습니다."),
    AT_EXPIRED_AND_RT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AT는 만료되었고 RT는 비어있습니다."),
    RT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "RT가 비어있습니다"),
    INVALID_PRINCIPAL(HttpStatus.UNAUTHORIZED, "잘못된 Principal입니다"),

    //모의고사
    PRACTICE_TEST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 모의고사를 찾을 수 없습니다"),

    //문항
    PROBLEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 문제를 찾을 수 없습니다"),
    PROBLEM_ALREADY_EXIST(HttpStatus.CONFLICT, "해당 문제는 이미 존재합니다"),
    INVALID_MULTIPLE_CHOICE_ANSWER(HttpStatus.BAD_REQUEST, "객관식 문제의 정답은 1~5 사이의 숫자여야 합니다"),
    INVALID_SHORT_NUMBER_ANSWER(HttpStatus.BAD_REQUEST, "주관식 문제의 정답은 0~999 사이의 숫자여야 합니다"),

    //개념태그
    CONCEPT_TAG_NOT_FOUND_IN_LIST(HttpStatus.NOT_FOUND, "해당 리스트 중 존재하지 않는 개념 태그가 있습니다."),

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
