package com.moplus.moplus_server.client.submit.service;


import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.dto.response.AllProblemGetResponse;
import com.moplus.moplus_server.client.submit.dto.response.ChildProblemClientGetResponse;
import com.moplus.moplus_server.client.submit.dto.response.DayProgress;
import com.moplus.moplus_server.client.submit.dto.response.ProblemClientGetResponse;
import com.moplus.moplus_server.client.submit.repository.ChildProblemSubmitRepository;
import com.moplus.moplus_server.client.submit.repository.ProblemSubmitRepository;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.repository.ChildProblemRepository;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemsGetService {

    private final PublishRepository publishRepository;
    private final ProblemSubmitRepository problemSubmitRepository;
    private final ProblemRepository problemRepository;
    private final ProblemSetRepository problemSetRepository;
    private final ChildProblemSubmitRepository childProblemSubmitRepository;
    private final ChildProblemRepository childProblemRepository;

    @Transactional(readOnly = true)
    public List<AllProblemGetResponse> getAllProblem(int year, int month) {
        Long memberId = 1L;

        if (month < 1 || month > 12) {
            throw new InvalidValueException(ErrorCode.INVALID_MONTH_ERROR);
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // 해당 월 모든 Publish 조회
        // 오늘 이후 발행이 있다면 필터링
        List<Publish> publishes = publishRepository.findByPublishedDateBetween(startDate, endDate).stream()
                .filter(publish -> !publish.getPublishedDate().isAfter(LocalDate.now()))
                .toList();

        List<AllProblemGetResponse> result = new ArrayList<>();

        for (Publish publish : publishes) {
            Long publishId = publish.getId();
            LocalDate date = publish.getPublishedDate();

            // 날짜별 사용자 제출 정보 조회
            List<ProblemSubmit> submissions = problemSubmitRepository.findByMemberIdAndPublishId(memberId, publishId);
            List<ProblemSubmitStatus> problemStatuses = submissions.stream()
                    .map(ProblemSubmit::getStatus )
                    .toList();

            // 사용자 제출 정보 바탕으로 진행도 결정
            DayProgress progress = DayProgress.determineDayProgress(problemStatuses);
            String mainProblemImageUrl = getMainProblemImageUrl(publish.getProblemSetId());

            result.add(AllProblemGetResponse.of(publishId, date, progress, problemStatuses, mainProblemImageUrl));
        }
        return result;
    }

    private String getMainProblemImageUrl(Long problemSetId) {
        return problemSetRepository.findById(problemSetId)
                .flatMap(problemSet -> problemSet.getProblemIds().stream().findFirst())
                .flatMap(problemRepository::findById)
                .map(Problem::getMainProblemImageUrl)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public ProblemClientGetResponse getProblem(Long publishId, Long problemId) {
        Long memberId = 1L;

        // 발행 조회
        Publish publish = publishRepository.findByIdElseThrow(publishId);
        denyAccessToFuturePublish(publish);

        // 문항 번호 추출
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(publish.getProblemSetId());
        List<Long> problemIds = problemSet.getProblemIds();
        int number = problemIds.indexOf(problemId);
        if (number == -1) {
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND_IN_PROBLEM_SET);
        }

        // 문항조회
        Problem problem = problemRepository.findByIdElseThrow(problemId);

        // 문항 제출 조회
        ProblemSubmit problemSubmit = problemSubmitRepository.findByMemberIdAndPublishIdAndProblemIdElseThrow(memberId,
                publishId, problemId);

        // 새끼 문항 제출 상태 조회
        List<Long> childProblemIds = problem.getChildProblems().stream()
                .map(ChildProblem::getId)
                .toList();

        List<ChildProblemSubmitStatus> childProblemStatuses = childProblemSubmitRepository.findAllByMemberIdAndPublishIdAndChildProblemIdIn(
                memberId, publishId, childProblemIds).stream()
                .map(ChildProblemSubmit::getStatus)
                .toList();

        return ProblemClientGetResponse.of(problem, problemSubmit.getStatus(), childProblemStatuses, number + 1);
    }

    @Transactional(readOnly = true)
    public ChildProblemClientGetResponse getChildProblem(Long publishId, Long problemId, Long childProblemId) {
        Long memberId = 1L;

        // 발행 조회
        Publish publish = publishRepository.findByIdElseThrow(publishId);
        denyAccessToFuturePublish(publish);

        // 문항/새끼문항 조회
        Problem problem = problemRepository.findByIdElseThrow(problemId);
        ChildProblem childProblem = childProblemRepository.findByIdElseThrow(childProblemId);

        // 새끼문항 제출 조회
        ChildProblemSubmit childProblemSubmit = childProblemSubmitRepository.findByMemberIdAndPublishIdAndChildProblemIdElseThrow(
                memberId, publishId, childProblemId);

        int sequence = 0;
        for (int i = 0; i < problem.getChildProblems().size(); i++) {
            if (problem.getChildProblems().get(i).getId().equals(childProblemId)) {
                sequence = i + 1;
                break;
            }
        }

        return ChildProblemClientGetResponse.of(problem.getNumber(), sequence, childProblem.getImageUrl(), childProblemSubmit.getStatus());
    }

    private void denyAccessToFuturePublish(Publish publish){
        if (publish.getPublishedDate().isAfter(LocalDate.now())) {
            throw new InvalidValueException(ErrorCode.FUTURE_PUBLISH_NOT_ACCESSIBLE);
        }
    }
}
