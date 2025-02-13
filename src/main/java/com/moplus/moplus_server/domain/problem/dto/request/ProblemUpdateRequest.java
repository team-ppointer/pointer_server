package com.moplus.moplus_server.domain.problem.dto.request;

import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import java.util.List;
import java.util.Set;

public record ProblemUpdateRequest(
        ProblemType problemType,
        Long practiceTestId,
        int number,
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
        List<ChildProblemUpdateRequest> updateChildProblems,
        List<Long> deleteChildProblems
) {
}
