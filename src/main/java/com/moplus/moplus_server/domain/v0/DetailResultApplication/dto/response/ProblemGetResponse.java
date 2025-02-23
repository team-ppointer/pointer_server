package com.moplus.moplus_server.domain.v0.DetailResultApplication.dto.response;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemForTest;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemImageForTest;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemRating;
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
            ProblemForTest problemForTest
    ) {
        ProblemRating problemRating = problemForTest.getProblemRating();
        ProblemImageForTest image = problemForTest.getImage();
        return ProblemGetResponse.builder()
                .problemNumber(problemForTest.getProblemNumber())
                .difficultLevel(problemRating.getDifficultyLevel())
                .correctRate(problemForTest.getCorrectRate())
                .rating(problemRating.getRating())
                .imageUrl(image.getImageUrl())
                .build();
    }
}
