package com.moplus.moplus_server.domain.TestResult.dto.response;

import com.moplus.moplus_server.domain.TestResult.dto.request.SolvingTimePostRequest;
import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;

@Builder
public record TestResultGetResponse(
    Long id,
    int score,
    Duration solvingTime,
    int rank,
    Duration averageSolvingTime,
    List<IncorrectProblemGetResponse> incorrectProblems
) {

    public static TestResultGetResponse of(TestResult testResult, int rank, Duration averageSolvingTime,
        List<IncorrectProblemGetResponse> incorrectProblems) {
        return TestResultGetResponse.builder()
            .id(testResult.getId())
            .score(testResult.getScore())
            .solvingTime(testResult.getSolvingTime())
            .averageSolvingTime(averageSolvingTime)
            .rank(rank)
            .incorrectProblems(incorrectProblems)
            .build();
    }
}
