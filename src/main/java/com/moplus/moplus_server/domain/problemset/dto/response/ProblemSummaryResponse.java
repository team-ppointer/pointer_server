package com.moplus.moplus_server.domain.problemset.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemSummaryResponse(
        String problemId,
        int number,
        String practiceTestName,
        String comment,
        String mainProblemImageUrl,
        List<String> tagNames,
        int sequence
) {
    public static ProblemSummaryResponse of(Problem problem, int sequence, String practiceTestName, List<String> tagNames) {

        return ProblemSummaryResponse.builder()
                .problemId(problem.getId().toString())
                .number(problem.getNumber())
                .comment(problem.getComment())
                .mainProblemImageUrl(problem.getMainProblemImageUrl())
                .sequence(sequence)
                .practiceTestName(practiceTestName)
                .tagNames(tagNames)
                .build();
    }
}
