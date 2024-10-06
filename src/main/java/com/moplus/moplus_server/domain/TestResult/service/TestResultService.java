package com.moplus.moplus_server.domain.TestResult.service;

import com.moplus.moplus_server.domain.TestResult.dto.request.IncorrectProblemPostRequest;
import com.moplus.moplus_server.domain.TestResult.entity.IncorrectProblem;
import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import com.moplus.moplus_server.domain.TestResult.entity.TestScoreCalculator;
import com.moplus.moplus_server.domain.TestResult.repository.TestResultRepository;
import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.domain.practiceTest.service.ProblemService;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestResultService {

    private final TestResultRepository testResultRepository;
    private final PracticeTestRepository practiceTestRepository;
    private final TestScoreCalculator testScoreCalculator;
    private final IncorrectProblemService incorrectProblemService;

    @Transactional
    public Long createTestResult(Long practiceTestId, List<IncorrectProblemPostRequest> requests) {
        /*
        TODO : 테스트 결과를 생성하는 api
            - 모의고사 정보를 가져옵니다.
            - 테스트 결과 엔티티 생성 후 저장
            - 틀린 문제 정보를 저장
            - 점수 계산
         */

        PracticeTest practiceTest = practiceTestRepository.findById(practiceTestId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));

        TestResult testResult = TestResult.fromPracticeTestId(practiceTestId);
        TestResult savedTestResult = testResultRepository.save(testResult);

        List<IncorrectProblem> incorrectProblems = incorrectProblemService.saveIncorrectProblems(requests,
            practiceTest.getId(), savedTestResult);

        int score = testScoreCalculator.calculateScore(incorrectProblems, practiceTest);
        testResult.addScore(score);

        return testResultRepository.save(testResult).getId();
    }
}
