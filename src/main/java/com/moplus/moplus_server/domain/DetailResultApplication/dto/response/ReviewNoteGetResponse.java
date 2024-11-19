package com.moplus.moplus_server.domain.DetailResultApplication.dto.response;

import com.moplus.moplus_server.domain.TestResult.dto.response.EstimatedRatingGetResponse;
import com.moplus.moplus_server.domain.TestResult.dto.response.IncorrectProblemGetResponse;
import com.moplus.moplus_server.domain.TestResult.dto.response.RatingTableGetResponse;
import com.moplus.moplus_server.domain.TestResult.dto.response.TestResultGetResponse;
import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import java.time.Duration;
import java.util.List;
import lombok.Builder;

@Builder
public record ReviewNoteGetResponse(
        Long testResultId,
        int score,
        String solvingTime,
        String averageSolvingTime,
        List<EstimatedRatingGetResponse> estimatedRatingGetResponses,
        List<IncorrectProblemGetResponse> incorrectProblems,
        List<ProblemGetResponse> forCurrentRating,
        List<ProblemGetResponse> forNextRating,
        List<ProblemGetResponse> forBeforeRating
) {

    public static ReviewNoteGetResponse of(
            TestResult testResult,
            Duration averageSolvingTime,
            List<EstimatedRatingGetResponse> estimatedRatingGetResponses,
            List<IncorrectProblemGetResponse> incorrectProblems,
            List<ProblemGetResponse> forCurrentRating,
            List<ProblemGetResponse> forNextRating,
            List<ProblemGetResponse> forBeforeRating
    ) {
        return ReviewNoteGetResponse.builder()
                .testResultId(testResult.getId())
                .score(testResult.getScore())
                .solvingTime(testResult.getSolvingTime().toString())
                .averageSolvingTime(averageSolvingTime.toString())
                .estimatedRatingGetResponses(estimatedRatingGetResponses)
                .incorrectProblems(incorrectProblems)
                .forCurrentRating(forCurrentRating)
                .forNextRating(forNextRating)
                .forBeforeRating(forBeforeRating)
                .build();
    }
}
