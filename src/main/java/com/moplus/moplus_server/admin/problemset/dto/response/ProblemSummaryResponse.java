package com.moplus.moplus_server.admin.problemset.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Builder;

@Builder
public record ProblemSummaryResponse(
        @NotNull(message = "문항 ID는 필수입니다")
        Long problemId,
        @NotNull(message = "문항 custom ID는 필수입니다")
        String problemCustomId,
        String problemTitle,
        String memo,
        String mainProblemImageUrl,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        Set<String> tagNames
) {
    public static ProblemSummaryResponse of(Problem problem, Set<String> tagNames) {

        return ProblemSummaryResponse.builder()
                .problemId(problem.getId())
                .problemCustomId(problem.getProblemCustomId())
                .memo(problem.getMemo())
                .problemTitle(problem.getTitle())
                .mainProblemImageUrl(problem.getMainProblemImageUrl())
                .tagNames(tagNames)
                .build();
    }
}
