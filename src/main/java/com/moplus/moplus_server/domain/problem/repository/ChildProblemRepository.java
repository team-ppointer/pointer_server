package com.moplus.moplus_server.domain.problem.repository;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildProblemRepository extends JpaRepository<ChildProblem, Long> {

    default ChildProblem findByIdElseThrow(Long childProblemId) {
        return findById(childProblemId).orElseThrow(() -> new NotFoundException(ErrorCode.CHILD_PROBLEM_NOT_FOUND));
    }
}
