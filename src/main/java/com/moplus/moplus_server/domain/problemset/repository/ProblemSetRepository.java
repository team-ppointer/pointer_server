package com.moplus.moplus_server.domain.problemset.repository;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSetRepository extends JpaRepository<ProblemSet, Long> {

    default ProblemSet findByIdElseThrow(Long problemSetId) {
        return findById(problemSetId).orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_SET_NOT_FOUND));
    }

    default void validatePublishableProblemSet(Long problemSetId) {
        ProblemSet problemSet = findByIdElseThrow(problemSetId);

        //이거 soft delete 어노테이션으로 자동화 해야함(리팩토링 필요)
        if (problemSet.isDeleted()) {
            throw new InvalidValueException(ErrorCode.PROBLEM_SET_DELETED);
        }

        if (!ProblemSetConfirmStatus.CONFIRMED.equals(problemSet.getConfirmStatus())) {
            throw new NotFoundException(ErrorCode.PROBLEM_SET_NOT_CONFIRMED);
        }
    }
}
