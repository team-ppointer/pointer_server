package com.moplus.moplus_server.domain.problem.dto.request;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;

public record ProblemPostRequest(
        ProblemType problemType,
        Long practiceTestId,
        int number
) {
    public Problem toEntity(PracticeTestTag practiceTestTag, ProblemAdminId problemAdminId) {
        return Problem.builder()
                .problemAdminId(problemAdminId)
                .practiceTestTag(practiceTestTag)
                .number(number)
                .problemType(problemType)
                .build();
    }
}
