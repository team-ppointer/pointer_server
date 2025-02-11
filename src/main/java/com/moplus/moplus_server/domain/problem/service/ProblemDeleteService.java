package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemDeleteService {

    private final ProblemRepository problemRepository;

    @Transactional
    public void deleteProblem(String problemId) {
        problemRepository.existsByIdElseThrow(new ProblemAdminId(problemId));
        problemRepository.deleteById(new ProblemAdminId(problemId));
    }
}
