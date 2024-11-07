package com.moplus.moplus_server.domain.TestResult.service;

import com.moplus.moplus_server.domain.TestResult.dto.request.IncorrectProblemPostRequest;
import com.moplus.moplus_server.domain.TestResult.dto.request.SolvingTimePostRequest;
import com.moplus.moplus_server.domain.TestResult.dto.response.RatingTableGetResponse;
import com.moplus.moplus_server.domain.TestResult.dto.response.TestResultGetResponse;
import com.moplus.moplus_server.domain.TestResult.entity.IncorrectProblem;
import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import com.moplus.moplus_server.domain.TestResult.entity.TestScoreCalculator;
import com.moplus.moplus_server.domain.TestResult.repository.TestResultRepository;
import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.domain.RatingTable;
import com.moplus.moplus_server.domain.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.domain.practiceTest.repository.RatingTableRepository;
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
    private final RatingTableRepository ratingTableRepository;

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

    public TestResultGetResponse getTestResult(Long testResultId) {
        TestResult testResult = testResultRepository.findById(testResultId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEST_RESULT_NOT_FOUND));
        PracticeTest practiceTest = getPracticeTestById(testResult.getPracticeTestId());
        Duration averageSolvingTime = practiceTest.getAverageSolvingTime();

        List<RatingTableGetResponse> ratingTableGetResponses = ratingTableRepository.findAllByPracticeTestId(
                        practiceTest.getId())
                .stream()
                .map(RatingTableGetResponse::from)
                .toList();

        return TestResultGetResponse.of(
                testResult,
                averageSolvingTime,
                incorrectProblemService.getResponsesByTestResultId(testResultId),
                ratingTableGetResponses
        );
    }

    @Transactional
    public TestResultGetResponse getTestResultBySolvingTime(Long testResultId, SolvingTimePostRequest request) {
        TestResult testResult = testResultRepository.findById(testResultId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEST_RESULT_NOT_FOUND));

        testResult.addSolvingTime(Duration.parse(request.solvingTime()));
        testResultRepository.save(testResult);
        PracticeTest practiceTest = getPracticeTestById(testResult.getPracticeTestId());
        Duration averageSolvingTime = practiceTest.getAverageSolvingTime();

        List<RatingTableGetResponse> ratingTableGetResponses = ratingTableRepository.findAllByPracticeTestId(
                        practiceTest.getId())
                .stream()
                .map(RatingTableGetResponse::from)
                .toList();


        return TestResultGetResponse.of(
                testResult,
                averageSolvingTime,
                incorrectProblemService.getResponsesByTestResultId(testResultId),
                ratingTableGetResponses
        );
    }
}
