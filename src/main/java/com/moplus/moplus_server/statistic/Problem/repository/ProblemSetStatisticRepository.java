package com.moplus.moplus_server.statistic.Problem.repository;

import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import com.moplus.moplus_server.statistic.Problem.domain.ProblemSetStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSetStatisticRepository extends JpaRepository<ProblemSetStatistic, Long> {
    default ProblemSetStatistic findByIdElseThrow(Long id) {
        return findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_SET_STATISTIC_NOT_FOUND));
    }
} 