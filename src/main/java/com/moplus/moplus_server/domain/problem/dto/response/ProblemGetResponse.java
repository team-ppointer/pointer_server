package com.moplus.moplus_server.domain.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import lombok.Builder;

@Builder
public record ProblemGetResponse(
        @NotNull(message = "문항 ID 필수입니다")
        Long id,
        @NotNull(message = "문항 custom ID는 필수입니다")
        String problemCustomId,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        Set<Long> conceptTagIds,
        boolean isConfirmed,
        Long practiceTestId,
        Integer number,
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
        @NotNull(message = "컬렉션 값은 필수입니다.")
        List<String> prescriptionImageUrls,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        List<ChildProblemGetResponse> childProblems,
        Integer recommendedMinute,
        Integer recommendedSecond
) {

    public static ProblemGetResponse of(Problem problem) {

        return ProblemGetResponse.builder()
                .id(problem.getId())
                .problemCustomId(problem.getProblemCustomId())
                .conceptTagIds(problem.getConceptTagIds())
                .isConfirmed(problem.isConfirmed())
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
                .recommendedMinute(problem.getRecommendedTime().getMinute())
                .recommendedSecond(problem.getRecommendedTime().getSecond())
                .build();
    }
}
