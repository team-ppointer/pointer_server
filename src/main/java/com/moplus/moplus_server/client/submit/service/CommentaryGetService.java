package com.moplus.moplus_server.client.submit.service;

import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.dto.response.ChildProblemDetailResponse;
import com.moplus.moplus_server.client.submit.dto.response.CommentaryGetResponse;
import com.moplus.moplus_server.client.submit.dto.response.PrescriptionResponse;
import com.moplus.moplus_server.client.submit.dto.response.ProblemDetailResponse;
import com.moplus.moplus_server.client.submit.repository.ChildProblemSubmitRepository;
import com.moplus.moplus_server.client.submit.repository.ProblemSubmitRepository;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentaryGetService {

    private final PublishRepository publishRepository;
    private final ProblemSubmitRepository problemSubmitRepository;
    private final ProblemRepository problemRepository;
    private final ProblemSetRepository problemSetRepository;
    private final ChildProblemSubmitRepository childProblemSubmitRepository;

    @Transactional(readOnly = true)
    public CommentaryGetResponse getCommentary(Long memberId, Long publishId, Long problemId) {

        // 발행 조회
        Publish publish = publishRepository.findByIdElseThrow(publishId);
        denyAccessToFuturePublish(publish);

        // 문항 제출 조회
        ProblemSubmit problemSubmit = problemSubmitRepository.findByMemberIdAndPublishIdAndProblemIdElseThrow(memberId,
                publishId, problemId);
        if (problemSubmit.getStatus() == ProblemSubmitStatus.IN_PROGRESS
                || problemSubmit.getStatus() == ProblemSubmitStatus.NOT_STARTED) {
            throw new InvalidValueException(ErrorCode.PROBLEM_SUBMIT_NOT_COMPLETED);
        }

        // 문항 해설 생성
        Problem problem = problemRepository.findByIdElseThrow(problemId);
        ProblemDetailResponse mainProblem = ProblemDetailResponse.of(
                problem,
                problemSubmit.getStatus()
        );

        // 새끼문항 해설 생성
        List<ChildProblemDetailResponse> childProblem = problem.getChildProblems().stream()
                .map(cp -> ChildProblemDetailResponse.of(
                        cp.getImageUrl(),
                        cp.getPrescriptionImageUrls(),
                        childProblemSubmitRepository.findByMemberIdAndPublishIdAndChildProblemId(memberId, publishId,
                                        cp.getId())
                                .map(ChildProblemSubmit::getStatus)
                                .orElse(ChildProblemSubmitStatus.NOT_STARTED)
                )).toList();

        // 처방 정보 생성
        PrescriptionResponse prescription = PrescriptionResponse.of(childProblem, mainProblem);

        // 문항 번호 추출
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(publish.getProblemSetId());
        List<Long> problemIds = problemSet.getProblemIds();
        int number = problemIds.indexOf(problemId);
        if (number == -1) {
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND_IN_PROBLEM_SET);
        }

        return CommentaryGetResponse.of(
                number + 1,
                problem,
                prescription
        );
    }

    private void denyAccessToFuturePublish(Publish publish){
        if (publish.getPublishedDate().isAfter(LocalDate.now())) {
            throw new InvalidValueException(ErrorCode.FUTURE_PUBLISH_NOT_ACCESSIBLE);
        }
    }
}
