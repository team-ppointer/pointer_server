package com.moplus.moplus_server.domain.TestResult.dto.response;

import com.moplus.moplus_server.domain.TestResult.dto.request.SolvingTimePostRequest;
import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;

@Builder
public record TestResultGetResponse(
    Long id,
    int score,
    String solvingTime,
    String averageSolvingTime,
    @Schema(description = "예상 등급", example = "3")
    int estimatedRating,
    List<IncorrectProblemGetResponse> incorrectProblems
) {

    public static TestResultGetResponse of(TestResult testResult, int rank, Duration averageSolvingTime,
        int solvingCount, List<IncorrectProblemGetResponse> incorrectProblems) {
        return TestResultGetResponse.builder()
            .id(testResult.getId())
            .score(testResult.getScore())
            .solvingTime(testResult.getSolvingTime().toString())
            .averageSolvingTime(averageSolvingTime.toString())
            .incorrectProblems(incorrectProblems)
            .build();
    }
}
