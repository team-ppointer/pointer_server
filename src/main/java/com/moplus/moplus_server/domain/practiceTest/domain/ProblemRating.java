package com.moplus.moplus_server.domain.practiceTest.domain;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemRating {

    EXTREME("극상위권", 0, 30, "최상"),
    TIER_1("1등급", 30, 50, "상"),
    TIER_2("2등급", 50, 60, "중상"),
    TIER_3("3등급", 60, 80, "중"),
    TIER_4("4등급", 80, 90, "중하"),
    OTHER("5등급 이하", 90, 100, "하"),
    ;

    private String rating;
    private double startCorrectRateRange;
    private double endCorrectRateRange;
    private String difficultyLevel;

    public static ProblemRating findProblemRating(Problem problem) {
        return Arrays.stream(values())
                .filter(problemRating -> problemRating.startCorrectRateRange <= problem.getCorrectRate()
                        && problemRating.endCorrectRateRange > problem.getCorrectRate())
                .findFirst()
                .orElse(OTHER);
    }
}
