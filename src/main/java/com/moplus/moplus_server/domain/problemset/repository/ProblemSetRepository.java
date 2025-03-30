package com.moplus.moplus_server.domain.problemset.repository;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSetRepository extends JpaRepository<ProblemSet, Long> {

    Logger log = LoggerFactory.getLogger(ProblemSetRepository.class);

    default ProblemSet findByIdElseThrow(Long problemSetId) {
        return findById(problemSetId).orElseThrow(() -> {
            log.atError().log("id " + problemSetId + "번 세트가 존재하지 않습니다.");
            throw new NotFoundException(ErrorCode.PROBLEM_SET_NOT_FOUND);
        });
    }

    default void validatePublishableProblemSet(Long problemSetId) {
        ProblemSet problemSet = findByIdElseThrow(problemSetId);

        if (!ProblemSetConfirmStatus.CONFIRMED.equals(problemSet.getConfirmStatus())) {
            throw new NotFoundException(ErrorCode.PROBLEM_SET_NOT_CONFIRMED);
        }
    }
}
