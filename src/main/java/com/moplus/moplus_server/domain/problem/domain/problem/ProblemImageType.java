package com.moplus.moplus_server.domain.problem.domain.problem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProblemImageType {
    MAIN_PROBLEM("main-problem", "문제 이미지"),
    MAIN_ANALYSIS("main-analysis", "분석 이미지"),
    MAIN_HANDWRITING_EXPLANATION("main-handwriting-explanation", "손글씨 설명 이미지"),
    READING_TIP("reading-tip", "읽기 팁 이미지"),
    SENIOR_TIP("senior-tip", "선배 팁 이미지"),
    PRESCRIPTION("prescription", "처방전 이미지"),
    CHILD_PROBLEM("child-problem", "하위 문제 이미지");

    private final String type;
    private final String description;
} 