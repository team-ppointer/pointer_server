package com.moplus.moplus_server.domain.problem.domain.problem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProblemType {

    GICHUL_PROBLEM("기출문제", 1),
    VARIANT_PROBLEM("변형문제", 2),
    CREATION_PROBLEM("창작문제", 3);

    private final String name;
    private final int code;

    public boolean isCreationProblem() {
        return this == CREATION_PROBLEM;
    }

    public boolean requiresPracticeTest() {
        return !isCreationProblem();
    }
}
