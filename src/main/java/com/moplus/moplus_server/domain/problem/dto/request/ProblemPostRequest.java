package com.moplus.moplus_server.domain.problem.dto.request;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import java.util.List;
import java.util.Set;

public record ProblemPostRequest(
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
        List<ChildProblemPostRequest> childProblems
) {
    public Problem toEntity(PracticeTestTag practiceTestTag, ProblemId problemId) {
        return Problem.builder()
                .id(problemId)
                .conceptTagIds(conceptTagIds)
                .practiceTestTag(practiceTestTag)
                .number(number)
                .answer(answer)
                .comment(comment)
                .mainProblemImageUrl(mainProblemImageUrl)
                .mainAnalysisImageUrl(mainAnalysisImageUrl)
                .readingTipImageUrl(readingTipImageUrl)
                .seniorTipImageUrl(seniorTipImageUrl)
                .prescriptionImageUrl(prescriptionImageUrl)
                .build();
    }
}
