package com.moplus.moplus_server.client.submit.repository;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildProblemSubmitRepository extends JpaRepository<ChildProblemSubmit, Long> {
    Optional<ChildProblemSubmit> findByMemberIdAndPublishIdAndChildProblemId(Long memberId, Long publishId,
                                                                             Long childProblemId);

    default ChildProblemSubmit findByMemberIdAndPublishIdAndChildProblemIdElseThrow(Long memberId, Long publishId,
                                                                                    Long childProblemId) {
        return findByMemberIdAndPublishIdAndChildProblemId(memberId, publishId, childProblemId).orElseThrow(
                () -> new NotFoundException(ErrorCode.CHILD_PROBLEM_SUBMIT_NOT_CONFIRMED));
    }

    List<ChildProblemSubmit> findAllByMemberIdAndPublishIdAndChildProblemIdIn(Long memberId, Long publishId,
                                                                              List<Long> childProblemIds);

    default List<ChildProblemSubmitStatus> findAllChildProblemSubmitStatusWithDefault(Long memberId, Long publishId,
                                                                                      List<Long> childProblemIds) {
        List<ChildProblemSubmit> submits = findAllByMemberIdAndPublishIdAndChildProblemIdIn(memberId, publishId,
                childProblemIds);

        // childProblemId를 key로 하는 Map 생성
        Map<Long, ChildProblemSubmitStatus> statusMap = submits.stream()
                .collect(Collectors.toMap(
                        ChildProblemSubmit::getChildProblemId,
                        ChildProblemSubmit::getStatus
                ));

        // 모든 childProblemId에 대해 status 리스트 생성
        return childProblemIds.stream()
                .map(id -> statusMap.getOrDefault(id, ChildProblemSubmitStatus.NOT_STARTED))
                .collect(Collectors.toList());
    }
}
