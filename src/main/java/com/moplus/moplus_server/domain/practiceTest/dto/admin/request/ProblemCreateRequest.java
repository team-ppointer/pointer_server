package com.moplus.moplus_server.domain.practiceTest.dto.admin.request;

import com.moplus.moplus_server.domain.practiceTest.domain.AnswerFormat;
import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.domain.Problem;

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
