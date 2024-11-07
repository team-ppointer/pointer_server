package com.moplus.moplus_server.domain.TestResult.dto.response;

import com.moplus.moplus_server.domain.TestResult.entity.EstimatedRating;
import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Duration;
import java.util.List;
import lombok.Builder;

@Builder
public record TestResultGetResponse(
        Long testResultId,
        int score,
        String solvingTime,
        String averageSolvingTime,
        List<EstimatedRatingGetResponse> estimatedRatingGetResponses,
        List<IncorrectProblemGetResponse> incorrectProblems,
        List<RatingTableGetResponse> ratingTables
) {

    public static TestResultGetResponse of(
            TestResult testResult,
            Duration averageSolvingTime,
            List<EstimatedRatingGetResponse> estimatedRatingGetResponses,
            List<IncorrectProblemGetResponse> incorrectProblems,
            List<RatingTableGetResponse> ratingTables
) {
        return TestResultGetResponse.builder()
                .testResultId(testResult.getId())
                .score(testResult.getScore())
                .solvingTime(testResult.getSolvingTime().toString())
                .averageSolvingTime(averageSolvingTime.toString())
                .estimatedRatingGetResponses(estimatedRatingGetResponses)
                .incorrectProblems(incorrectProblems)
                .ratingTables(ratingTables)
                .build();
    }
}
