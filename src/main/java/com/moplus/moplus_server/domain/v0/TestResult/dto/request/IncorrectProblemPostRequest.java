package com.moplus.moplus_server.domain.v0.TestResult.dto.request;

import com.moplus.moplus_server.domain.v0.TestResult.entity.IncorrectProblem;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemForTest;

public record IncorrectProblemPostRequest(
        String problemNumber,
        String incorrectAnswer
) {

    public IncorrectProblem toEntity(ProblemForTest problemForTest) {
        return IncorrectProblem.builder()
                .problemNumber(problemNumber)
                .incorrectAnswer(incorrectAnswer)
                .point(problemForTest.getPoint())
                .problemId(problemForTest.getId())
                .build();
    }
}
