package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.repository.ChildProblemRepository;
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
    private final ChildProblemRepository childProblemRepository;

    @Transactional
    public Long createChildProblem(Long problemId) {
        Problem problem = problemRepository.findByIdElseThrow(problemId);
        if (problem.isConfirmed()) {
            throw new InvalidValueException(ErrorCode.CHILD_PROBLEM_UPDATE_AFTER_CONFIRMED);
        }

        problem.addChildProblem(ChildProblem.createEmptyChildProblem());

        return problemRepository.save(problem).getChildProblems().get(problem.getChildProblems().size() - 1).getId();
    }

    @Transactional
    public void deleteChildProblem(Long problemId, Long childProblemId) {
        Problem problem = problemRepository.findByIdElseThrow(problemId);
        if (problem.isConfirmed()) {
            throw new InvalidValueException(ErrorCode.CHILD_PROBLEM_UPDATE_AFTER_CONFIRMED);
        }
        problem.deleteChildProblem(childProblemId);
        problemRepository.save(problem);
    }
}
