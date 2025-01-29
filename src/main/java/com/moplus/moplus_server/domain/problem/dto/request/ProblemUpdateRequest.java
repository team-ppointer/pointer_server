package com.moplus.moplus_server.domain.problem.dto.request;

import java.util.List;
import java.util.Set;

public record ProblemUpdateRequest(
        Set<Long> conceptTagIds,
        Long practiceTestId,
        int number,
        int answer,
        String comment,
        String mainProblemImageUrl,
        String mainAnalysisImageUrl,
        String readingTipImageUrl,
        String seniorTipImageUrl,
        String prescriptionImageUrl,
        List<ChildProblemUpdateRequest> updateChildProblems,
        List<ChildProblemPostRequest> createChildProblems,
        List<ChildProblemDeleteRequest> deleteChildProblems
) {
}
