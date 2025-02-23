package com.moplus.moplus_server.domain.v0.practiceTest.dto.admin.request;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.AnswerFormat;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemForTest;

public record ProblemCreateRequest(
        String problemNumber,
        String answerFormat,
        String answer,
        int point,
        double correctRate
) {

    public ProblemForTest toEntity(PracticeTest practiceTest) {
        return ProblemForTest.builder()
                .problemNumber(this.problemNumber())
                .answerFormat(AnswerFormat.fromValue(this.answerFormat))
                .answer(this.answer)
                .point(this.point())
                .correctRate(this.correctRate)
                .practiceTest(practiceTest)
                .build();
    }
}
