package com.moplus.moplus_server.domain.TestResult.dto.response;

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
        @Schema(description = "예상 등급", example = "3")
        int estimatedRating,
        List<IncorrectProblemGetResponse> incorrectProblems,
        List<RatingTableGetResponse> ratingTables
) {

    public static TestResultGetResponse of(
            TestResult testResult,
            Duration averageSolvingTime,
            List<IncorrectProblemGetResponse> incorrectProblems,
            List<RatingTableGetResponse> ratingTables
) {
        return TestResultGetResponse.builder()
                .testResultId(testResult.getId())
                .score(testResult.getScore())
                .solvingTime(testResult.getSolvingTime().toString())
                .averageSolvingTime(averageSolvingTime.toString())
                .incorrectProblems(incorrectProblems)
                .ratingTables(ratingTables)
                .build();
    }
}
