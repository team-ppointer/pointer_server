package com.moplus.moplus_server.domain.problem.dto.request;

import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public record ProblemUpdateRequest(
        @NotNull(message = "문제 유형은 필수입니다")
        ProblemType problemType,
        Long practiceTestId,
        Integer number,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        Set<Long> conceptTagIds,
        String answer,
        String title,
        Integer difficulty,
        String memo,
        String mainProblemImageUrl,
        String mainAnalysisImageUrl,
        String mainHandwritingExplanationImageUrl,
        String readingTipImageUrl,
        String seniorTipImageUrl,
        List<String> prescriptionImageUrls,
        AnswerType answerType,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        List<ChildProblemUpdateRequest> updateChildProblems,
        Integer recommendedMinute,
        Integer recommendedSecond
) {
}
