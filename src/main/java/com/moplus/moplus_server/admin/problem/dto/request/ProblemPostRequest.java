package com.moplus.moplus_server.admin.problem.dto.request;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemCustomId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import jakarta.validation.constraints.NotNull;

public record ProblemPostRequest(
        @NotNull(message = "문항 유형은 필수입니다")
        ProblemType problemType,
        Long practiceTestId,
        int number
) {
    public Problem toEntity(PracticeTestTag practiceTestTag, ProblemCustomId problemCustomId) {
        return Problem.builder()
                .problemCustomId(problemCustomId)
                .practiceTestTag(practiceTestTag)
                .number(number)
                .title("")
                .problemType(problemType)
                .build();
    }
}