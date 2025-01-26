package com.moplus.moplus_server.domain.v0.practiceTest.dto.client.response;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemForTest;
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

    public static ProblemGetResponse from(ProblemForTest problemForTest) {
        return ProblemGetResponse.builder()
                .id(problemForTest.getId())
                .answer(problemForTest.getAnswer())
                .problemNumber(problemForTest.getProblemNumber())
                .answerFormat(problemForTest.getAnswerFormat().getValue())
                .point(problemForTest.getPoint())
                .correctRate(problemForTest.getCorrectRate())
                .build();
    }
}
