package com.moplus.moplus_server.client.submit.service;


import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.dto.request.ChildProblemSubmitCreateRequest;
import com.moplus.moplus_server.client.submit.dto.request.ChildProblemSubmitUpdateIncorrectRequest;
import com.moplus.moplus_server.client.submit.dto.request.ChildProblemSubmitUpdateRequest;
import com.moplus.moplus_server.client.submit.dto.request.ProblemSubmitCreateRequest;
import com.moplus.moplus_server.client.submit.dto.request.ProblemSubmitUpdateRequest;
import com.moplus.moplus_server.client.submit.dto.response.ChildProblemSubmitUpdateResponse;
import com.moplus.moplus_server.client.submit.repository.ChildProblemSubmitRepository;
import com.moplus.moplus_server.client.submit.repository.ProblemSubmitRepository;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.repository.ChildProblemRepository;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetGetService;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import com.moplus.moplus_server.statistic.Problem.domain.StatisticEntityTarget;
import com.moplus.moplus_server.statistic.Problem.domain.StatisticFieldType;
import com.moplus.moplus_server.statistic.Problem.service.CountStatisticsGetService;
import com.moplus.moplus_server.statistic.Problem.service.CountStatisticsUpdateService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientSubmitService {

    private final ProblemSubmitRepository problemSubmitRepository;
    private final ProblemRepository problemRepository;
    private final ChildProblemSubmitRepository childProblemSubmitRepository;
    private final PublishRepository publishRepository;
    private final ChildProblemRepository childProblemRepository;
    private final CountStatisticsUpdateService countStatisticsUpdateService;
    private final ProblemSetGetService problemSetGetService;
    private final ProblemSetRepository problemSetRepository;
    private final CountStatisticsGetService countStatisticsGetService;

    private static Long getFirstProblemInProblemSet(ProblemSet problemSet) {
        return problemSet.getProblemIds().get(0);
    }

    @Transactional
    public void createProblemSubmit(Long memberId, ProblemSubmitCreateRequest request) {

        // 존재하는 발행인지 검증
        Publish publish = publishRepository.findByIdElseThrow(request.publishId());
        // 존재하는 문항인지 검증
        problemRepository.existsByIdElseThrow(request.problemId());

        // 제출이력이 없을때만 생성
        Optional<ProblemSubmit> existingProblemSubmit = problemSubmitRepository.findByMemberIdAndPublishIdAndProblemId(
                memberId,
                request.publishId(), request.problemId());
        if (existingProblemSubmit.isEmpty()) {
            ProblemSubmit problemSubmit = request.toEntity(memberId);
            problemSubmitRepository.save(problemSubmit);
        }

        //문제 풀이 통계 업데이트
        countStatisticsUpdateService.createStatistics(request.problemId(), StatisticEntityTarget.PROBLEM);
        countStatisticsUpdateService.updateStatistics(request.problemId(), StatisticFieldType.SUBMIT,
                StatisticEntityTarget.PROBLEM);
        countStatisticsUpdateService.updateStatistics(request.problemId(), StatisticFieldType.VIEW,
                StatisticEntityTarget.PROBLEM);

        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(publish.getProblemSetId());
        if (getFirstProblemInProblemSet(problemSet).equals(request.problemId())) {
            //TODO: 현재는 첫번째 문항을 풀었을 때 set 풀이 count가 올라가지만 나중에는 어떤 문제를 풀든 첫 문제를 풀면 count가 올라가야해요
            countStatisticsUpdateService.createStatistics(publish.getProblemSetId(), StatisticEntityTarget.PROBLEM_SET);
            countStatisticsUpdateService.updateStatistics(publish.getProblemSetId(), StatisticFieldType.SUBMIT,
                    StatisticEntityTarget.PROBLEM_SET);
            countStatisticsUpdateService.updateStatistics(publish.getProblemSetId(), StatisticFieldType.VIEW,
                    StatisticEntityTarget.PROBLEM_SET);
        }
    }

    @Transactional
    public ProblemSubmitStatus updateProblemSubmit(Long memberId, ProblemSubmitUpdateRequest request) {

        // 존재하는 발행인지 검증
        publishRepository.existsByIdElseThrow(request.publishId());

        // 문항 조회
        Problem problem = problemRepository.findByIdElseThrow(request.problemId());

        //문항 제출 데이터 조회
        ProblemSubmit problemSubmit = problemSubmitRepository.findByMemberIdAndPublishIdAndProblemIdElseThrow(memberId,
                request.publishId(), request.problemId());
        // 제출한 답안에 대한 상태 결정
        ProblemSubmitStatus status = ProblemSubmitStatus.determineStatus(problemSubmit.getStatus(), request.answer(),
                problem.getAnswer());

        problemSubmit.updateStatus(status);
        return status;
    }

    @Transactional
    public void createChildProblemSubmit(Long memberId, ChildProblemSubmitCreateRequest request) {

        // 존재하는 발행인지 검증
        publishRepository.existsByIdElseThrow(request.publishId());
        // 존재하는 문항인지 검증
        problemRepository.existsByIdElseThrow(request.problemId());

        // 문항제출 이력이 없으면 문항제출 생성
        Optional<ProblemSubmit> existingProblemSubmit = problemSubmitRepository.findByMemberIdAndPublishIdAndProblemId(
                memberId,
                request.publishId(), request.problemId());
        if (existingProblemSubmit.isEmpty()) {
            ProblemSubmit problemSubmit = ProblemSubmit.builder()
                    .memberId(memberId)
                    .publishId(request.publishId())
                    .problemId(request.problemId())
                    .status(ProblemSubmitStatus.IN_PROGRESS)
                    .build();
            problemSubmitRepository.save(problemSubmit);
        }

        // 문항의 새끼문항 조회
        Problem problem = problemRepository.findByIdElseThrow(request.problemId());
        List<ChildProblem> childProblems = problem.getChildProblems();

        // 제출이력이 없을떄만 생성
        for (ChildProblem childProblem : childProblems) {
            Long childProblemId = childProblem.getId();

            Optional<ChildProblemSubmit> existingChildProblemSubmit = childProblemSubmitRepository.findByMemberIdAndPublishIdAndChildProblemId(
                    memberId,
                    request.publishId(), childProblemId);
            if (existingChildProblemSubmit.isEmpty()) {
                ChildProblemSubmit childProblemSubmit = ChildProblemSubmit.builder()
                        .memberId(memberId)
                        .publishId(request.publishId())
                        .childProblemId(childProblemId)
                        .status(ChildProblemSubmitStatus.NOT_STARTED)
                        .build();
                childProblemSubmitRepository.save(childProblemSubmit);
            }
        }
    }

    @Transactional
    public ChildProblemSubmitUpdateResponse updateChildProblemSubmit(Long memberId,
                                                                     ChildProblemSubmitUpdateRequest request) {

        // 존재하는 발행인지 검증
        publishRepository.existsByIdElseThrow(request.publishId());

        // 새끼문항 조회
        ChildProblem childProblem = childProblemRepository.findByIdElseThrow(request.childProblemId());

        //새끼문항 제출 데이터 조회
        ChildProblemSubmit childProblemSubmit = childProblemSubmitRepository.findByMemberIdAndPublishIdAndChildProblemIdElseThrow(
                memberId,
                request.publishId(), request.childProblemId());
        // 제출한 답안에 대한 상태 결정
        ChildProblemSubmitStatus status = ChildProblemSubmitStatus.determineStatus(childProblemSubmit.getStatus(),
                request.answer(),
                childProblem.getAnswer());

        childProblemSubmit.updateStatus(status);
        return ChildProblemSubmitUpdateResponse.of(status, childProblem.getAnswer());
    }

    @Transactional
    public void updateChildProblemSubmitIncorrect(Long memberId, ChildProblemSubmitUpdateIncorrectRequest request) {

        // 존재하는 발행인지 검증
        publishRepository.existsByIdElseThrow(request.publishId());

        // 새끼문항 조회
        ChildProblem childProblem = childProblemRepository.findByIdElseThrow(request.childProblemId());

        //새끼문항 제출 데이터 조회
        ChildProblemSubmit childProblemSubmit = childProblemSubmitRepository.findByMemberIdAndPublishIdAndChildProblemIdElseThrow(
                memberId,
                request.publishId(), request.childProblemId());
        // 틀림 처리
        childProblemSubmit.updateStatusIncorrect();
    }
}
