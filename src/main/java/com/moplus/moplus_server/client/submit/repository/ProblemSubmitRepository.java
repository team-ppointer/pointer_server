package com.moplus.moplus_server.client.submit.repository;

import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSubmitRepository extends JpaRepository<ProblemSubmit, Long> {
    List<ProblemSubmit> findByMemberIdAndPublishId(Long memberId, Long publishId);

    Optional<ProblemSubmit> findByMemberIdAndPublishIdAndProblemId(Long memberId, Long publishId, Long problemId);

    default ProblemSubmit findByMemberIdAndPublishIdAndProblemIdElseThrow(Long memberId, Long publishId,
                                                                          Long problemId) {
        return findByMemberIdAndPublishIdAndProblemId(memberId, publishId, problemId).orElseThrow(
                () -> new NotFoundException(ErrorCode.PROBLEM_SUBMIT_NOT_CONFIRMED));
    }
} 