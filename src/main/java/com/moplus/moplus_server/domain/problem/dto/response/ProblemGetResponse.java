package com.moplus.moplus_server.domain.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import java.util.List;
import java.util.Set;
import lombok.Builder;

@Builder
public record ProblemGetResponse(
        String problemId,
        Set<Long> conceptTagIds,
        Long practiceTestId,
        int number,
        String answer,
        String comment,
        String mainProblemImageUrl,
        String mainAnalysisImageUrl,
        String readingTipImageUrl,
        String seniorTipImageUrl,
        String prescriptionImageUrl,
        List<ChildProblemGetResponse> childProblems
) {

    public static ProblemGetResponse of(Problem problem) {
        return ProblemGetResponse.builder()
                .problemId(problem.getId().toString())
                .conceptTagIds(problem.getConceptTagIds())
                .practiceTestId(problem.getPracticeTestId())
                .number(problem.getNumber())
                .answer(problem.getAnswer())
                .comment(problem.getComment())
                .mainProblemImageUrl(problem.getMainProblemImageUrl())
                .mainAnalysisImageUrl(problem.getMainAnalysisImageUrl())
                .readingTipImageUrl(problem.getReadingTipImageUrl())
                .seniorTipImageUrl(problem.getSeniorTipImageUrl())
                .prescriptionImageUrl(problem.getPrescriptionImageUrl())
                .childProblems(problem.getChildProblems().stream()
                        .map(ChildProblemGetResponse::of)
                        .toList())
                .build();
    }
}
