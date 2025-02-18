package com.moplus.moplus_server.domain.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import jakarta.validation.constraints.NotNull;

public record ProblemPostResponse(
        @NotNull(message = "문항 ID는 필수입니다")
        Long id,
        @NotNull(message = "문항 custom ID는 필수입니다")
        Long problemCustomId
) {
    public static ProblemPostResponse of(Problem problem) {
        return new ProblemPostResponse(problem.getId(), problem.getPracticeTestId());
    }
}
