package com.moplus.moplus_server.client.submit.service;


import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.dto.request.ChildProblemSubmitCreateRequest;
import com.moplus.moplus_server.client.submit.dto.request.ChildProblemSubmitUpdateRequest;
import com.moplus.moplus_server.client.submit.dto.request.ProblemSubmitCreateRequest;
import com.moplus.moplus_server.client.submit.dto.request.ProblemSubmitUpdateRequest;
import com.moplus.moplus_server.client.submit.repository.ChildProblemSubmitRepository;
import com.moplus.moplus_server.client.submit.repository.ProblemSubmitRepository;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.repository.ChildProblemRepository;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
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

    @Transactional
    public void createProblemSubmit(ProblemSubmitCreateRequest request) {
        Long memberId = 1L;

        // 존재하는 발행인지 검증
        publishRepository.existsByIdElseThrow(request.publishId());
        // 존재하는 문항인지 검증
        problemRepository.existsByIdElseThrow(request.problemId());

        // 제출이력이 없을때만 생성
        Optional<ProblemSubmit> existingProblemSubmit = problemSubmitRepository.findByMemberIdAndPublishIdAndProblemId(memberId,
                request.publishId(), request.problemId());
        if (existingProblemSubmit.isEmpty()) {
            ProblemSubmit problemSubmit = request.toEntity(memberId);
            problemSubmitRepository.save(problemSubmit);
        }
    }

    @Transactional
    public ProblemSubmitStatus updateProblemSubmit(ProblemSubmitUpdateRequest request) {
        Long memberId = 1L;

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
    public void createChildProblemSubmit(ChildProblemSubmitCreateRequest request) {
        Long memberId = 1L;

        // 존재하는 발행인지 검증
        publishRepository.existsByIdElseThrow(request.publishId());
        // 존재하는 문항인지 검증
        problemRepository.existsByIdElseThrow(request.problemId());

        // 문항제출 이력이 없으면 문항제출 생성
        Optional<ProblemSubmit> existingProblemSubmit = problemSubmitRepository.findByMemberIdAndPublishIdAndProblemId(memberId,
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

            Optional<ChildProblemSubmit> existingChildProblemSubmit = childProblemSubmitRepository.findByMemberIdAndPublishIdAndChildProblemId(memberId,
                    request.publishId(), childProblemId);
            ChildProblemSubmit childProblemSubmit = ChildProblemSubmit.builder()
                    .memberId(memberId)
                    .publishId(request.publishId())
                    .childProblemId(childProblemId)
                    .status(ChildProblemSubmitStatus.NOT_STARTED)
                    .build();
            childProblemSubmitRepository.save(childProblemSubmit);
        }
    }

    @Transactional
    public ChildProblemSubmitStatus updateChildProblemSubmit(ChildProblemSubmitUpdateRequest request) {
        Long memberId = 1L;

        // 존재하는 발행인지 검증
        publishRepository.existsByIdElseThrow(request.publishId());

        // 새끼문항 조회
        ChildProblem childProblem = childProblemRepository.findByIdElseThrow(request.childProblemId());

        //새끼문항 제출 데이터 조회
        ChildProblemSubmit childProblemSubmit = childProblemSubmitRepository.findByMemberIdAndPublishIdAndChildProblemIdElseThrow(memberId,
                request.publishId(), request.childProblemId());
        // 제출한 답안에 대한 상태 결정
        ChildProblemSubmitStatus status = ChildProblemSubmitStatus.determineStatus(childProblemSubmit.getStatus(), request.answer(),
                childProblem.getAnswer());

        childProblemSubmit.updateStatus(status);
        return status;
    }

}
