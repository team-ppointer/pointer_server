package com.moplus.moplus_server.domain.problem.repository;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.global.error.exception.AlreadyExistException;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, ProblemId> {

    boolean existsByPracticeTestIdAndNumber(Long practiceTestId, int number);

    default void existsByPracticeTestIdAndNumberOrThrow(Long practiceTestId, int number) {
        if (existsByPracticeTestIdAndNumber(practiceTestId, number)) {
            throw new AlreadyExistException(ErrorCode.PROBLEM_ALREADY_EXIST);
        }
    }

    default void existsByIdElseThrow(ProblemId problemId) {
        if (!existsById(problemId)) {
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND);
        }
    }


    default Problem findByIdElseThrow(ProblemId problemId) {
        return findById(problemId).orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));
    }
}
