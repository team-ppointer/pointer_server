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
    PROBLEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 문항을 찾을 수 없습니다"),
    PROBLEM_ALREADY_EXIST(HttpStatus.CONFLICT, "해당 문항는 이미 존재합니다"),
    INVALID_MULTIPLE_CHOICE_ANSWER(HttpStatus.BAD_REQUEST, "객관식 문항의 정답은 1~5 사이의 숫자여야 합니다"),
    INVALID_SHORT_NUMBER_ANSWER(HttpStatus.BAD_REQUEST, "주관식 문항의 정답은 0~999 사이의 숫자여야 합니다"),
    INVALID_CONFIRM_PROBLEM(HttpStatus.BAD_REQUEST, "유효하지 않은 문항들 : "),
    INVALID_DIFFICULTY(HttpStatus.BAD_REQUEST, "난이도는 1~10 사이의 숫자여야 합니다"),
    PROBLEM_NOT_FOUND_IN_PROBLEM_SET(HttpStatus.NOT_FOUND, "해당 날짜에 발행된 문항세트에 존재하는 문항이 아닙니다."),
    PROBLEM_NUMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "번호에 해당하는 문항을 찾을 수 없습니다."),

    //새끼 문항
    CHILD_PROBLEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 새끼 문제를 찾을 수 없습니다"),
    CHILD_PROBLEM_UPDATE_AFTER_CONFIRMED(HttpStatus.BAD_REQUEST, "컨펌 후 문제는 수정할 수 없습니다"),
    INVALID_CHILD_PROBLEM_SEQUENCE(HttpStatus.BAD_REQUEST, "새끼 문제의 업데이트 순서가 일치하지 않습니다."),
    INVALID_CHILD_PROBLEM_SIZE(HttpStatus.BAD_REQUEST, "새끼 문제의 업데이트 개수가 일치하지 않습니다."),


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

    //문항세트
    PROBLEM_SET_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 문항세트를 찾을 수 없습니다"),
    EMPTY_PROBLEMS_ERROR(HttpStatus.BAD_REQUEST, "적어도 1개의 문항을 등록해주세요"),
    DELETE_PROBLEM_SET_GET_ERROR(HttpStatus.BAD_REQUEST, "삭제된 세트 문항은 조회할 수 없습니다."),
    DELETE_PROBLEM_SET_UPDATE_ERROR(HttpStatus.BAD_REQUEST, "삭제된 세트 문항은 수정할 수 없습니다."),
    DELETE_PROBLEM_SET_TOGGLE_ERROR(HttpStatus.BAD_REQUEST, "삭제된 세트 문항은 컨펌을 토글할 수 없습니다."),
    CONFIRMED_PROBLEM_SET_REORDER_ERROR(HttpStatus.BAD_REQUEST, "컨펌된 세트 문항은 문항 순서를 변경할 수 없습니다."),
    CONFIRMED_PROBLEM_SET_UPDATE_ERROR(HttpStatus.BAD_REQUEST, "컨펌된 세트 문항은 수정할 수 없습니다."),

    // 발행
    INVALID_MONTH_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않은 월입니다."),
    INVALID_DATE_ERROR(HttpStatus.BAD_REQUEST, "오늘 이후 날짜에만 발행이 가능합니다."),
    PUBLISH_NOT_FOUND(HttpStatus.NOT_FOUND, "발행 정보를 찾을 수 없습니다"),
    CANNOT_DELETE_PAST_PUBLISH(HttpStatus.BAD_REQUEST, "이미 지난 발행건은 삭제할 수 없습니다."),
    ALREADY_PUBLISHED_ERROR(HttpStatus.BAD_REQUEST, "이미 발행된 문항세트는 컨펌해제할 수 없습니다."),
    PROBLEM_SET_DELETED(HttpStatus.BAD_REQUEST, "삭제된 문항세트는 발행할 수 없습니다"),
    PROBLEM_SET_NOT_CONFIRMED(HttpStatus.BAD_REQUEST, "컨펌되지 않은 문항세트는 발행할 수 없습니다"),

    // 통계
    PROBLEM_STATISTIC_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 문항의 통계 정보를 찾을 수 없습니다"),
    PROBLEM_SET_STATISTIC_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 문항세트의 통계 정보를 찾을 수 없습니다"),
    CHILD_PROBLEM_STATISTIC_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 새끼 문항의 통계 정보를 찾을 수 없습니다"),
    FUTURE_PUBLISH_NOT_ACCESSIBLE(HttpStatus.BAD_REQUEST, "오늘 이후의 발행을 조회할 수 없습니다."),

    // 문항 제출
    PROBLEM_SUBMIT_NOT_CONFIRMED(HttpStatus.NOT_FOUND, "문항 제출 정보를 찾을 수 없습니다."),

    // 새끼문항 제출
    CHILD_PROBLEM_SUBMIT_NOT_CONFIRMED(HttpStatus.NOT_FOUND, "새끼문항 제출 정보를 찾을 수 없습니다."),
    ;


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
