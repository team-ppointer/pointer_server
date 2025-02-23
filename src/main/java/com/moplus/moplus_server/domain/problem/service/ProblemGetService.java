package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemGetService {

    private final ProblemRepository problemRepository;

    @Transactional(readOnly = true)
    public ProblemGetResponse getProblem(Long problemId) {
        Problem problem = problemRepository.findByIdElseThrow(problemId);
        return ProblemGetResponse.of(problem);
    }
}
