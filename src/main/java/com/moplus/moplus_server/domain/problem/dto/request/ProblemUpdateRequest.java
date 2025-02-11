package com.moplus.moplus_server.domain.problem.dto.request;

import java.util.List;
import java.util.Set;

public record ProblemUpdateRequest(
        Set<Long> conceptTagIds,
        String answer,
        String comment,
        String mainProblemImageUrl,
        String mainAnalysisImageUrl,
        String readingTipImageUrl,
        String seniorTipImageUrl,
        String prescriptionImageUrl,
        List<ChildProblemUpdateRequest> updateChildProblems,
        List<Long> deleteChildProblems
) {
}
