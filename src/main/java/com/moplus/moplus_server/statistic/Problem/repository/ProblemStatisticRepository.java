package com.moplus.moplus_server.statistic.Problem.repository;

import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import com.moplus.moplus_server.statistic.Problem.domain.ProblemStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemStatisticRepository extends JpaRepository<ProblemStatistic, Long> {
    default ProblemStatistic findByIdElseThrow(Long id) {
        return findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_STATISTIC_NOT_FOUND));
    }
} 