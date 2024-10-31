package com.moplus.moplus_server.domain.practiceTest.dto.response;

import com.moplus.moplus_server.domain.practiceTest.entity.Problem;
import lombok.Builder;

@Builder
public record ProblemGetResponse(
        Long id,
        String problemNumber,
        String answerFormat,
        String answer,
        int point,
        double correctRate
) {

    public static ProblemGetResponse from(Problem problem) {
        return ProblemGetResponse.builder()
                .id(problem.getId())
                .answer(problem.getAnswer())
                .problemNumber(problem.getProblemNumber())
                .answerFormat(problem.getAnswerFormat().getValue())
                .point(problem.getPoint())
                .correctRate(problem.getCorrectRate())
                .build();
    }
}
