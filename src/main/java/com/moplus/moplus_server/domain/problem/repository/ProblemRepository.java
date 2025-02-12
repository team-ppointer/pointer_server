package com.moplus.moplus_server.domain.problem.repository;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    boolean existsByProblemAdminId(ProblemAdminId problemAdminId);

    default void existsByIdElseThrow(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND);
        }
    }

    default Problem findByIdElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));
    }
}
