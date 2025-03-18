package com.moplus.moplus_server.client.submit.repository;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmit;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildProblemSubmitRepository extends JpaRepository<ChildProblemSubmit, Long> {
    Optional<ChildProblemSubmit> findByMemberIdAndPublishIdAndChildProblemId(Long memberId, Long publishId, Long childProblemId);

    default ChildProblemSubmit findByMemberIdAndPublishIdAndChildProblemIdElseThrow(Long memberId, Long publishId, Long childProblemId) {
        return findByMemberIdAndPublishIdAndChildProblemId(memberId, publishId, childProblemId).orElseThrow(
                () -> new NotFoundException(ErrorCode.CHILD_PROBLEM_SUBMIT_NOT_CONFIRMED));
    }
}
