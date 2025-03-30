package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChildProblemService {

    private final ProblemRepository problemRepository;

    @Transactional
    public Long createChildProblem(Long problemId) {
        Problem problem = findAndValidateProblem(problemId);
        ChildProblem newChildProblem = ChildProblem.createEmptyChildProblem();
        problem.addChildProblem(newChildProblem);
        problemRepository.flush();
        return getCreatedChildProblemId(problem);
    }

    @Transactional
    public void deleteChildProblem(Long problemId, Long childProblemId) {
        Problem problem = findAndValidateProblem(problemId);
        problem.deleteChildProblem(childProblemId);
    }

    private Problem findAndValidateProblem(Long problemId) {
        Problem problem = problemRepository.findByIdElseThrow(problemId);
        if (problem.isConfirmed()) {
            throw new InvalidValueException(ErrorCode.CHILD_PROBLEM_UPDATE_AFTER_CONFIRMED);
        }
        return problem;
    }

    private Long getCreatedChildProblemId(Problem problem) {
        return problem.getChildProblems()
            .get(problem.getChildProblems().size() - 1)
            .getId();
    }
}
