package com.moplus.moplus_server.domain.DetailResultApplication.dto.response;

import com.moplus.moplus_server.domain.practiceTest.domain.Problem;
import com.moplus.moplus_server.domain.practiceTest.domain.ProblemImage;
import com.moplus.moplus_server.domain.practiceTest.domain.ProblemRating;
import lombok.Builder;

@Builder
public record ProblemGetResponse(
        String problemNumber,
        String difficultLevel,
        double correctRate,
        String rating,
        String imageUrl
) {

    public static ProblemGetResponse of(
            Problem problem
    ) {
        ProblemRating problemRating = problem.getProblemRating();
        ProblemImage image = problem.getImage();
        return ProblemGetResponse.builder()
                .problemNumber(problem.getProblemNumber())
                .difficultLevel(problemRating.getDifficultyLevel())
                .correctRate(problem.getCorrectRate())
                .rating(problemRating.getRating())
                .imageUrl(image.getImageUrl())
                .build();
    }
}
