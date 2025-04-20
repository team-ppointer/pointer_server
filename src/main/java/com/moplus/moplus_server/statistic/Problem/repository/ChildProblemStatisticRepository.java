package com.moplus.moplus_server.statistic.Problem.repository;

import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import com.moplus.moplus_server.statistic.Problem.domain.ChildProblemStatistic;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildProblemStatisticRepository extends JpaRepository<ChildProblemStatistic, Long> {

    Optional<ChildProblemStatistic> findByChildProblemId(Long childProblemId);

    default ChildProblemStatistic findByChildProblemIdOrElse(Long childProblemId) {
        return findByChildProblemId(childProblemId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHILD_PROBLEM_STATISTIC_NOT_FOUND));
    }
} 