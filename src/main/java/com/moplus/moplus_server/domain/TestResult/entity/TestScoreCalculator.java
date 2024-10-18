package com.moplus.moplus_server.domain.TestResult.entity;

import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestScoreCalculator {

    public int calculateScore(List<IncorrectProblem> incorrectProblems, PracticeTest practiceTest){
        int minusPoint = incorrectProblems.stream()
            .mapToInt(IncorrectProblem::getPoint)
            .sum();

        int score = practiceTest.getSubject().getPerfectScore() - minusPoint;

        if (score < 0) {
            throw new InvalidValueException(ErrorCode.INVALID_SCORE);
        }

        return score;
    }

}
