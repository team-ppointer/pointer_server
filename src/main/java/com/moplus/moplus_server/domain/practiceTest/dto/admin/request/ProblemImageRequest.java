package com.moplus.moplus_server.domain.practiceTest.dto.admin.request;

import com.moplus.moplus_server.domain.practiceTest.domain.Problem;
import lombok.Builder;

@Builder
public record ProblemImageRequest(
        Long problemId,
        String problemNumber,
        String imageUrl
) {

    public static ProblemImageRequest of(Problem problem) {
        return ProblemImageRequest.builder()
                .problemId(problem.getId())
                .problemNumber(problem.getProblemNumber())
                .imageUrl(problem.getImage() != null ? problem.getImage().getImageUrl() : null)
                .build();
    }
}
