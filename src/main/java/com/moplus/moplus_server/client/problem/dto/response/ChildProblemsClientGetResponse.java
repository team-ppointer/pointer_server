package com.moplus.moplus_server.client.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record ChildProblemsClientGetResponse(
        @NotNull(message = "메인문항 이미지URL은 필수입니다.")
        String mainProblemImageUrl,
        @NotNull(message = "새끼문항ID 리스트는 필수입니다.")
        List<Long> childProblemIds
) {
    public static ChildProblemsClientGetResponse of(Problem problem) {
        return ChildProblemsClientGetResponse.builder()
                .mainProblemImageUrl(problem.getMainProblemImageUrl())
                .childProblemIds(problem.getChildProblems().stream().map(ChildProblem::getId).toList())
                .build();
    }
}
