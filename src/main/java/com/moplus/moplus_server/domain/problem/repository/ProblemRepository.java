package com.moplus.moplus_server.domain.problem.repository;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, ProblemAdminId> {


    default void existsByIdElseThrow(ProblemAdminId problemAdminId) {
        if (!existsById(problemAdminId)) {
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND);
        }
    }

    default Problem findByIdElseThrow(ProblemAdminId problemAdminId) {
        return findById(problemAdminId).orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));
    }
}
