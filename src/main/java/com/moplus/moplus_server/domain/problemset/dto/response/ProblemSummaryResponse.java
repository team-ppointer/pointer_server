package com.moplus.moplus_server.domain.problemset.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemSummaryResponse(
        String problemId,
        int number,
        String practiceTestName,
        String memo,
        String mainProblemImageUrl,
        List<String> tagNames
) {
    public static ProblemSummaryResponse of(Problem problem, String practiceTestName, List<String> tagNames) {

        return ProblemSummaryResponse.builder()
                .problemId(problem.getProblemAdminId().getId())
                .number(problem.getNumber())
                .memo(problem.getMemo())
                .mainProblemImageUrl(problem.getMainProblemImageUrl())
                .practiceTestName(practiceTestName)
                .tagNames(tagNames)
                .build();
    }
}
