package com.moplus.moplus_server.domain.v0.TestResult.service;

import com.moplus.moplus_server.domain.v0.TestResult.dto.request.IncorrectProblemPostRequest;
import com.moplus.moplus_server.domain.v0.TestResult.dto.request.SolvingTimePostRequest;
import com.moplus.moplus_server.domain.v0.TestResult.dto.response.EstimatedRatingGetResponse;
import com.moplus.moplus_server.domain.v0.TestResult.dto.response.RatingTableGetResponse;
import com.moplus.moplus_server.domain.v0.TestResult.dto.response.TestResultGetResponse;
import com.moplus.moplus_server.domain.v0.TestResult.entity.EstimatedRating;
import com.moplus.moplus_server.domain.v0.TestResult.entity.IncorrectProblem;
import com.moplus.moplus_server.domain.v0.TestResult.entity.TestResult;
import com.moplus.moplus_server.domain.v0.TestResult.entity.TestScoreCalculator;
import com.moplus.moplus_server.domain.v0.TestResult.repository.EstimatedRatingRepository;
import com.moplus.moplus_server.domain.v0.TestResult.repository.TestResultRepository;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.RatingTable;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.RatingTableRepository;
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
    private final EstimatedRatingRepository estimatedRatingRepository;

    private static boolean hasRawScore(RatingTable ratingTable) {
        return !ratingTable.getRatingRows().get(0).getRawScores().isBlank();
    }

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

        List<RatingTableGetResponse> ratingTableGetResponses = getRatingTableGetResponses(
                practiceTest);

        List<EstimatedRatingGetResponse> estimatedRatingGetResponses = estimatedRatingRepository.findAllByTestResultId(
                        testResultId).stream()
                .map(EstimatedRatingGetResponse::from)
                .toList();

        return TestResultGetResponse.of(
                testResult,
                averageSolvingTime,
                estimatedRatingGetResponses,
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

        List<RatingTableGetResponse> ratingTableGetResponses = getRatingTableGetResponses(
                practiceTest);

        List<EstimatedRatingGetResponse> estimatedRatingGetResponses = ratingTableRepository.findAllByPracticeTestId(
                        practiceTest.getId()).stream()
                .filter(TestResultService::hasRawScore)
                .map(ratingTable -> EstimatedRating.of(testResult.getScore(), testResultId, ratingTable))
                .map(estimatedRatingRepository::save)
                .map(EstimatedRatingGetResponse::from)
                .toList();

        return TestResultGetResponse.of(
                testResult,
                averageSolvingTime,
                estimatedRatingGetResponses,
                incorrectProblemService.getResponsesByTestResultId(testResultId),
                ratingTableGetResponses
        );
    }

    private List<RatingTableGetResponse> getRatingTableGetResponses(PracticeTest practiceTest) {
        return ratingTableRepository.findAllByPracticeTestId(
                        practiceTest.getId())
                .stream()
                .filter(TestResultService::hasRawScore)
                .map(RatingTableGetResponse::from)
                .toList();
    }
}
