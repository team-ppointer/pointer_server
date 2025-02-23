package com.moplus.moplus_server.domain.v0.TestResult.dto.response;

import com.moplus.moplus_server.domain.v0.TestResult.entity.IncorrectProblem;
import lombok.Builder;

@Builder
public record IncorrectProblemGetResponse(
        String problemNumber,
        double correctRate
) {

    public static IncorrectProblemGetResponse from(IncorrectProblem incorrectProblem) {
        return IncorrectProblemGetResponse.builder()
                .problemNumber(incorrectProblem.getProblemNumber())
                .correctRate(incorrectProblem.getCorrectRate())
                .build();
    }

}
