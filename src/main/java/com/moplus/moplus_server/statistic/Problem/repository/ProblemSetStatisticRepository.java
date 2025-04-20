package com.moplus.moplus_server.statistic.Problem.repository;

import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import com.moplus.moplus_server.statistic.Problem.domain.ProblemSetStatistic;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSetStatisticRepository extends JpaRepository<ProblemSetStatistic, Long> {

    Optional<ProblemSetStatistic> findByProblemSetId(Long id);

    default ProblemSetStatistic findByProblemSetIdElseThrow(Long id) {
        return findByProblemSetId(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_SET_STATISTIC_NOT_FOUND));
    }
} 