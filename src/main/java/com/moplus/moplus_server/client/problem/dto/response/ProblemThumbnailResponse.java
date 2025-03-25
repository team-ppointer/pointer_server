package com.moplus.moplus_server.client.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import lombok.Builder;

@Builder
public record ProblemThumbnailResponse(
        int number,
        String imageUrl,
        Integer recommendedMinute,
        Integer recommendedSecond
) {
    public static ProblemThumbnailResponse of(int number, Problem problem) {
        return ProblemThumbnailResponse.builder()
                .number(number)
                .imageUrl(problem.getMainProblemImageUrl())
                .recommendedMinute(problem.getRecommendedTime().getMinute())
                .recommendedSecond(problem.getRecommendedTime().getSecond())
                .build();
    }
}