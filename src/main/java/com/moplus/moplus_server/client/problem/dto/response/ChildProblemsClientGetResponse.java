package com.moplus.moplus_server.client.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import java.util.List;
import lombok.Builder;

@Builder
public record ChildProblemsClientGetResponse(
        String mainProblemImageUrl,
        List<Long> childProblemIds
) {
    public static ChildProblemsClientGetResponse of(Problem problem) {
        return ChildProblemsClientGetResponse.builder()
                .mainProblemImageUrl(problem.getMainProblemImageUrl())
                .childProblemIds(problem.getChildProblems().stream().map(ChildProblem::getId).toList())
                .build();
    }
}
