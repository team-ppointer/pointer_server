package com.moplus.moplus_server.domain.problemset.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemSummaryResponse(
        @NotNull(message = "문항 ID는 필수입니다")
        Long problemId,
        @NotNull(message = "문항 custom ID는 필수입니다")
        String problemCustomId,
        int number,
        String practiceTestName,
        String memo,
        String mainProblemImageUrl,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        List<String> tagNames
) {
    public static ProblemSummaryResponse of(Problem problem, String practiceTestName, List<String> tagNames) {

        return ProblemSummaryResponse.builder()
                .problemId(problem.getId())
                .problemCustomId(problem.getProblemCustomId())
                .number(problem.getNumber())
                .memo(problem.getMemo())
                .mainProblemImageUrl(problem.getMainProblemImageUrl())
                .practiceTestName(practiceTestName)
                .tagNames(tagNames)
                .build();
    }
}
