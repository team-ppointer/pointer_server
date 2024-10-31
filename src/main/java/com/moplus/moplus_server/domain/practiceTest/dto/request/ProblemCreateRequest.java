package com.moplus.moplus_server.domain.practiceTest.dto.request;

import com.moplus.moplus_server.domain.practiceTest.entity.AnswerFormat;
import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.entity.Problem;

public record ProblemCreateRequest(
        String problemNumber,
        String answerFormat,
        String answer,
        int point,
        double correctRate
) {

    public Problem toEntity(PracticeTest practiceTest) {
        return Problem.builder()
                .problemNumber(this.problemNumber())
                .answerFormat(AnswerFormat.fromValue(this.answerFormat))
                .answer(this.answer)
                .point(this.point())
                .correctRate(this.correctRate)
                .practiceTest(practiceTest)
                .build();
    }
}
