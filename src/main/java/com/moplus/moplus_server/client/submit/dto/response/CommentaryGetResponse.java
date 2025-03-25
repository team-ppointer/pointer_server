package com.moplus.moplus_server.client.submit.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import lombok.Builder;

@Builder
public record CommentaryGetResponse(
        int problemNumber,
        String answer,
        String mainAnalysisImageUrl,
        String mainHandwritingExplanationImageUrl,
        String readingTipImageUrl,
        String seniorTipImageUrl,
        AnswerType answerType,
        PrescriptionResponse prescription
) {
    public static CommentaryGetResponse of(int problemNumber, Problem problem, PrescriptionResponse prescription) {
        return CommentaryGetResponse.builder()
                .problemNumber(problemNumber)
                .answer(problem.getAnswer())
                .mainAnalysisImageUrl(problem.getMainAnalysisImageUrl())
                .mainHandwritingExplanationImageUrl(problem.getMainHandwritingExplanationImageUrl())
                .readingTipImageUrl(problem.getReadingTipImageUrl())
                .seniorTipImageUrl(problem.getSeniorTipImageUrl())
                .answerType(problem.getAnswerType())
                .prescription(prescription)
                .build();
    }
}
