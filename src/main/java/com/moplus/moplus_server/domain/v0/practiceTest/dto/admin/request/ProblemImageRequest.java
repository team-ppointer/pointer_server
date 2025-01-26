package com.moplus.moplus_server.domain.v0.practiceTest.dto.admin.request;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemForTest;
import lombok.Builder;

@Builder
public record ProblemImageRequest(
        Long problemId,
        String problemNumber,
        String imageUrl
) {

    public static ProblemImageRequest of(ProblemForTest problemForTest) {
        return ProblemImageRequest.builder()
                .problemId(problemForTest.getId())
                .problemNumber(problemForTest.getProblemNumber())
                .imageUrl(problemForTest.getImage() != null ? problemForTest.getImage().getImageUrl() : null)
                .build();
    }
}
