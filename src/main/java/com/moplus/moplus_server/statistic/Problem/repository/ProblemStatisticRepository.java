package com.moplus.moplus_server.statistic.Problem.repository;

import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import com.moplus.moplus_server.statistic.Problem.domain.ProblemStatistic;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProblemStatisticRepository extends JpaRepository<ProblemStatistic, Long> {

    Optional<ProblemStatistic> findByProblemId(Long problemId);

    default ProblemStatistic findByProblemIdElseThrow(Long problemId) {
        return findByProblemId(problemId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_STATISTIC_NOT_FOUND));
    }
} 