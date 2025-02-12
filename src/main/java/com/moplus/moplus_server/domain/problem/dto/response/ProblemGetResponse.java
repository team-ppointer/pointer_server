package com.moplus.moplus_server.domain.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import java.util.List;
import java.util.Set;
import lombok.Builder;

@Builder
public record ProblemGetResponse(
        String problemId,
        Set<Long> conceptTagIds,
        Long practiceTestId,
        int number,
        Integer difficulty,
        String title,
        String answer,
        String memo,
        ProblemType problemType,
        AnswerType answerType,
        String mainProblemImageUrl,
        String mainHandwritingExplanationImageUrl,
        String mainAnalysisImageUrl,
        String readingTipImageUrl,
        String seniorTipImageUrl,
        List<String> prescriptionImageUrls,
        List<ChildProblemGetResponse> childProblems
) {

    public static ProblemGetResponse of(Problem problem) {
        return ProblemGetResponse.builder()
                .problemId(problem.getProblemAdminId().getId())
                .conceptTagIds(problem.getConceptTagIds())
                .practiceTestId(problem.getPracticeTestId())
                .number(problem.getNumber())
                .answer(problem.getAnswer())
                .title(problem.getTitle())
                .difficulty(problem.getDifficulty())
                .memo(problem.getMemo())
                .problemType(problem.getProblemType())
                .answerType(problem.getAnswerType())
                .mainProblemImageUrl(problem.getMainProblemImageUrl())
                .mainHandwritingExplanationImageUrl(problem.getMainHandwritingExplanationImageUrl())
                .mainAnalysisImageUrl(problem.getMainAnalysisImageUrl())
                .readingTipImageUrl(problem.getReadingTipImageUrl())
                .seniorTipImageUrl(problem.getSeniorTipImageUrl())
                .prescriptionImageUrls(problem.getPrescriptionImageUrls())
                .childProblems(problem.getChildProblems().stream()
                        .map(ChildProblemGetResponse::of)
                        .toList())
                .build();
    }
}
