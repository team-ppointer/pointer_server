package com.moplus.moplus_server.domain.problemset.service;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemSetDeleteService {

    private final ProblemSetRepository problemSetRepository;

    @Transactional
    public void deleteProblemSet(Long problemSetId) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);
        problemSetRepository.delete(problemSet);
    }

}