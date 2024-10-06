package com.moplus.moplus_server.domain.TestResult.dto.request;

import com.moplus.moplus_server.domain.TestResult.entity.IncorrectProblem;
import com.moplus.moplus_server.domain.practiceTest.entity.Problem;

public record IncorrectProblemPostRequest(
    String problemNumber,
    String incorrectAnswer
) {

    public IncorrectProblem toEntity(Problem problem){
        return IncorrectProblem.builder()
            .problemNumber(problemNumber)
            .incorrectAnswer(incorrectAnswer)
            .point(problem.getPoint())
            .problemId(problem.getId())
            .build();
    }
}
