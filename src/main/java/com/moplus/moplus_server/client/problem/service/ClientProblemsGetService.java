package com.moplus.moplus_server.client.problem.service;


import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.client.problem.dto.response.AllProblemGetResponse;
import com.moplus.moplus_server.client.problem.dto.response.ChildProblemClientGetResponse;
import com.moplus.moplus_server.client.problem.dto.response.ChildProblemsClientGetResponse;
import com.moplus.moplus_server.client.problem.dto.response.ProblemClientGetResponse;
import com.moplus.moplus_server.client.problem.dto.response.ProblemClientThumbnailResponse;
import com.moplus.moplus_server.client.problem.dto.response.ProblemFeedProgressesGetResponse;
import com.moplus.moplus_server.client.problem.dto.response.PublishClientGetResponse;
import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.dto.response.DayProgress;
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
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientProblemsGetService {

    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
    private final PublishRepository publishRepository;
    private final ProblemSubmitRepository problemSubmitRepository;
    private final ProblemRepository problemRepository;
    private final ProblemSetRepository problemSetRepository;
    private final ChildProblemSubmitRepository childProblemSubmitRepository;
    private final ChildProblemRepository childProblemRepository;

    @Transactional(readOnly = true)
    public PublishClientGetResponse getProblemsInPublish(Long memberId, Long publishId) {
        Publish publish = publishRepository.findByIdElseThrow(publishId);
        denyAccessToFuturePublish(publish);

        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(publish.getProblemSetId());
        List<Long> problemIds = problemSet.getProblemIds();
        List<Problem> problems = problemRepository.findAllById(problemIds);

        List<ProblemFeedProgressesGetResponse> problemClientGetResponses = problems.stream()
                .map(problem -> getProblemStatus(memberId, publishId, problem.getId()))
                .toList();

        return PublishClientGetResponse.of(publish, problemSet, problemClientGetResponses);
    }

    @Transactional(readOnly = true)
    public List<AllProblemGetResponse> getAllProblem(Long memberId, int year, int month) {

        if (month < MIN_MONTH || month > MAX_MONTH) {
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

            // 문항세트의 전체 문제 목록 조회
            ProblemSet problemSet = problemSetRepository.findByIdElseThrow(publish.getProblemSetId());
            List<Long> problemIds = problemSet.getProblemIds();

            // 사용자 제출 정보 조회
            List<ProblemSubmit> submissions = problemSubmitRepository.findByMemberIdAndPublishId(memberId, publishId);
            Map<Long, ProblemSubmitStatus> submitStatusMap = submissions.stream()
                    .collect(Collectors.toMap(
                            ProblemSubmit::getProblemId,
                            ProblemSubmit::getStatus
                    ));

            // 모든 문항에 대해 상태 리스트 구성
            List<ProblemSubmitStatus> problemStatuses = problemIds.stream()
                    .map(id -> submitStatusMap.getOrDefault(id, ProblemSubmitStatus.NOT_STARTED))
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
    public ProblemClientGetResponse getProblem(Long memberId, Long publishId, Long problemId) {

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

        List<ChildProblemSubmitStatus> childProblemStatuses = childProblemSubmitRepository
                .findAllChildProblemSubmitStatusWithDefault(memberId, publishId, childProblemIds);

        return ProblemClientGetResponse.of(problem, problemSubmit.getStatus(), childProblemStatuses, number + 1);
    }

    @Transactional(readOnly = true)
    public ChildProblemClientGetResponse getChildProblem(Long memberId, Long publishId, Long problemId,
                                                         Long childProblemId) {

        // 발행 조회
        Publish publish = publishRepository.findByIdElseThrow(publishId);
        denyAccessToFuturePublish(publish);

        // 문항/새끼문항 조회
        Problem problem = problemRepository.findByIdElseThrow(problemId);
        ChildProblem childProblem = childProblemRepository.findByIdElseThrow(childProblemId);

        // 새끼문항 제출 조회
        ChildProblemSubmit childProblemSubmit = childProblemSubmitRepository.findByMemberIdAndPublishIdAndChildProblemIdElseThrow(
                memberId, publishId, childProblemId);

        int childProblemNumber = 0;
        for (int i = 0; i < problem.getChildProblems().size(); i++) {
            if (problem.getChildProblems().get(i).getId().equals(childProblemId)) {
                childProblemNumber = i + 1;
                break;
            }
        }

        // 문항 번호 추출
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(publish.getProblemSetId());
        List<Long> problemIds = problemSet.getProblemIds();
        int problemNumber = problemIds.indexOf(problemId);
        if (problemNumber == -1) {
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND_IN_PROBLEM_SET);
        }

        return ChildProblemClientGetResponse.of(problemNumber + 1, childProblemNumber, childProblem,
                childProblemSubmit.getStatus());
    }

    private void denyAccessToFuturePublish(Publish publish) {
        if (publish.getPublishedDate().isAfter(LocalDate.now())) {
            throw new InvalidValueException(ErrorCode.FUTURE_PUBLISH_NOT_ACCESSIBLE);
        }
    }

    private ProblemFeedProgressesGetResponse getProblemStatus(Long memberId, Long publishId, Long problemId) {
        // 문항 조회
        Problem problem = problemRepository.findByIdElseThrow(problemId);

        // 문항 제출 상태 조회 (없으면 NOT_STARTED)
        ProblemSubmitStatus problemStatus = problemSubmitRepository
                .findByMemberIdAndPublishIdAndProblemId(memberId, publishId, problemId)
                .map(ProblemSubmit::getStatus)
                .orElse(ProblemSubmitStatus.NOT_STARTED);

        // 새끼 문항 제출 상태 조회
        List<Long> childProblemIds = problem.getChildProblems().stream()
                .map(ChildProblem::getId)
                .toList();

        List<ChildProblemSubmitStatus> childProblemStatuses = new ArrayList<>(childProblemSubmitRepository
                .findAllByMemberIdAndPublishIdAndChildProblemIdIn(memberId, publishId, childProblemIds)
                .stream()
                .map(ChildProblemSubmit::getStatus)
                .toList());

        // 새끼 문항이 있는데 제출 상태가 없는 경우 NOT_STARTED 추가
        if (childProblemStatuses.size() < childProblemIds.size()) {
            int remainingCount = childProblemIds.size() - childProblemStatuses.size();
            for (int i = 0; i < remainingCount; i++) {
                childProblemStatuses.add(ChildProblemSubmitStatus.NOT_STARTED);
            }
        }

        return ProblemFeedProgressesGetResponse.of(problemStatus, childProblemStatuses, problemId);
    }

    @Transactional(readOnly = true)
    public ProblemClientThumbnailResponse getProblemThumbnail(Long publishId, Long problemId) {
        // 발행 조회
        Publish publish = publishRepository.findByIdElseThrow(publishId);
        denyAccessToFuturePublish(publish);

        // 문항 세트 조회
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(publish.getProblemSetId());
        List<Long> problemIds = problemSet.getProblemIds();

        // 문항 번호 추출
        int problemNumber = problemIds.indexOf(problemId);
        if (problemNumber == -1) {
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND_IN_PROBLEM_SET);
        }

        //문항 조회
        Problem problem = problemRepository.findByIdElseThrow(problemId);
        return ProblemClientThumbnailResponse.of(problemNumber + 1, problem);
    }

    @Transactional(readOnly = true)
    public ChildProblemsClientGetResponse getChildProblems(Long publishId, Long problemId) {
        // 발행 조회
        Publish publish = publishRepository.findByIdElseThrow(publishId);
        denyAccessToFuturePublish(publish);

        // 문항 세트 조회
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(publish.getProblemSetId());
        List<Long> problemIds = problemSet.getProblemIds();

        // 발행된 문항세트에 포함된 문항인지 검사
        int problemNumber = problemIds.indexOf(problemId);
        if (problemNumber == -1) {
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND_IN_PROBLEM_SET);
        }

        //문항 조회
        Problem problem = problemRepository.findByIdElseThrow(problemId);
        return ChildProblemsClientGetResponse.of(problem);
    }
}
