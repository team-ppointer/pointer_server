package com.moplus.moplus_server.client.submit.dto.response;

import lombok.Builder;

@Builder
public record CommentaryGetResponse(
        int problemNumber,
        String answer,
        String mainAnalysisImageUrl,
        String mainHandwritingExplanationImageUrl,
        String readingTipImageUrl,
        String seniorTipImageUrl,
        PrescriptionResponse prescription
) {
    public static CommentaryGetResponse of(int problemNumber, String answer, String mainAnalysisImageUrl,
                                           String mainHandwritingExplanationImageUrl, String readingTipImageUrl,
                                           String seniorTipImageUrl, PrescriptionResponse prescription) {
        return CommentaryGetResponse.builder()
                .problemNumber(problemNumber)
                .answer(answer)
                .mainAnalysisImageUrl(mainAnalysisImageUrl)
                .mainHandwritingExplanationImageUrl(mainHandwritingExplanationImageUrl)
                .readingTipImageUrl(readingTipImageUrl)
                .seniorTipImageUrl(seniorTipImageUrl)
                .prescription(prescription)
                .build();
    }
}
