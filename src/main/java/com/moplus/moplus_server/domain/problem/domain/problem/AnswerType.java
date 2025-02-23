package com.moplus.moplus_server.domain.problem.domain.problem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AnswerType {
    MULTIPLE_CHOICE("객관식"),
    SHORT_ANSWER("주관식");


    private final String name;
}
