package com.moplus.moplus_server.domain.TestResult.service;

import com.moplus.moplus_server.domain.TestResult.dto.request.IncorrectProblemPostRequest;
import com.moplus.moplus_server.domain.TestResult.dto.request.SolvingTimePostRequest;
import com.moplus.moplus_server.domain.TestResult.dto.response.TestResultGetResponse;
import com.moplus.moplus_server.domain.TestResult.entity.IncorrectProblem;
import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import com.moplus.moplus_server.domain.TestResult.entity.TestScoreCalculator;
import com.moplus.moplus_server.domain.TestResult.repository.TestResultRepository;
import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.time.Duration;
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
        PracticeTest practiceTest = getPracticeTestById(practiceTestId);

        TestResult testResult = TestResult.fromPracticeTestId(practiceTestId);
        TestResult savedTestResult = testResultRepository.save(testResult);

        List<IncorrectProblem> incorrectProblems = incorrectProblemService.saveIncorrectProblems(requests,
            practiceTest.getId(), savedTestResult);

        int score = testScoreCalculator.calculateScore(incorrectProblems, practiceTest);
        testResult.addScore(score);

        return testResultRepository.save(testResult).getId();
    }

    private PracticeTest getPracticeTestById(Long practiceTestId) {
        return practiceTestRepository.findById(practiceTestId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));
    }

    @Transactional
    public TestResultGetResponse getTestResultBySolvingTime(Long testResultId, SolvingTimePostRequest request) {
        TestResult testResult = testResultRepository.findById(testResultId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TEST_RESULT_NOT_FOUND));

        testResult.addSolvingTime(request.solvingTime());
        testResultRepository.save(testResult);

        Duration averageSolvingTime = getPracticeTestById(testResult.getPracticeTestId()).getAverageSolvingTime();

        List<TestResult> testResultsOrderByScoreDesc = testResultRepository.findByPracticeTestIdOrderByScoreDesc(
            testResult.getPracticeTestId());

        int rank = 0;
        for (int i = 0; i < testResultsOrderByScoreDesc.size(); i++) {
            int tempScore = testResultsOrderByScoreDesc.get(i).getScore();
            if(tempScore == testResult.getScore())
                rank = i + 1;
        }

        return TestResultGetResponse.of(testResult, rank, averageSolvingTime,
            incorrectProblemService.getResponsesByTestResultId(testResultId));
    }
}
