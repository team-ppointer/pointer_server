package com.moplus.moplus_server.domain.DetailResultApplication.service;

import com.moplus.moplus_server.domain.DetailResultApplication.dto.request.DetailResultApplicationPostRequest;
import com.moplus.moplus_server.domain.DetailResultApplication.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.DetailResultApplication.dto.response.ReviewNoteGetResponse;
import com.moplus.moplus_server.domain.DetailResultApplication.respository.DetailResultApplicationRepository;
import com.moplus.moplus_server.domain.TestResult.dto.response.EstimatedRatingGetResponse;
import com.moplus.moplus_server.domain.TestResult.entity.IncorrectProblem;
import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import com.moplus.moplus_server.domain.TestResult.repository.EstimatedRatingRepository;
import com.moplus.moplus_server.domain.TestResult.repository.IncorrectProblemRepository;
import com.moplus.moplus_server.domain.TestResult.repository.TestResultRepository;
import com.moplus.moplus_server.domain.TestResult.service.IncorrectProblemService;
import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.domain.Problem;
import com.moplus.moplus_server.domain.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.domain.practiceTest.repository.ProblemRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetailResultApplicationService {

    private final DetailResultApplicationRepository detailResultApplicationRepository;
    private final TestResultRepository testResultRepository;
    private final PracticeTestRepository practiceTestRepository;
    private final EstimatedRatingRepository estimatedRatingRepository;
    private final IncorrectProblemService incorrectProblemService;
    private final IncorrectProblemRepository incorrectProblemRepository;
    private final ProblemRepository problemRepository;

    @Transactional
    public void saveApplication(DetailResultApplicationPostRequest request) {
        detailResultApplicationRepository.save(request.toEntity());
    }

    public ReviewNoteGetResponse getReviewNoteInfo(Long testResultId) {
        TestResult testResult = testResultRepository.findById(testResultId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEST_RESULT_NOT_FOUND));

        PracticeTest practiceTest = getPracticeTestById(testResult.getPracticeTestId());
        Duration averageSolvingTime = practiceTest.getAverageSolvingTime();

        List<EstimatedRatingGetResponse> estimatedRatingGetResponses = estimatedRatingRepository.findAllByTestResultId(testResultId).stream()
                .map(EstimatedRatingGetResponse::from)
                .toList();

        int 대성마이맥_rating = estimatedRatingGetResponses.get(0).estimatedRating();

        List<Problem> incorrectProblems = incorrectProblemRepository.findAllByTestResultId(testResultId).stream()
                .map(IncorrectProblem::getProblemId)
                .map(problemId -> problemRepository.findById(problemId).orElseThrow())
                .toList();

        List<ProblemGetResponse> forCurrentRating = incorrectProblems.stream()
                .filter(problem -> problem.getProblemRating().getRatingValue() == 대성마이맥_rating)
                .map(ProblemGetResponse::of)
                .toList();

        List<ProblemGetResponse> forNextRating = incorrectProblems.stream()
                .filter(problem -> problem.getProblemRating().getRatingValue() == 대성마이맥_rating - 1)
                .map(ProblemGetResponse::of)
                .toList();

        List<ProblemGetResponse> forBeforeRating = incorrectProblems.stream()
                .filter(problem -> problem.getProblemRating().getRatingValue() >= 대성마이맥_rating + 1)
                .map(ProblemGetResponse::of)
                .toList();

        return ReviewNoteGetResponse.of(
                testResult,
                averageSolvingTime,
                estimatedRatingGetResponses,
                incorrectProblemService.getResponsesByTestResultId(testResultId),
                forCurrentRating,
                forNextRating,
                forBeforeRating
        );
    }

    private PracticeTest getPracticeTestById(Long practiceTestId) {
        return practiceTestRepository.findById(practiceTestId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));
    }
}
