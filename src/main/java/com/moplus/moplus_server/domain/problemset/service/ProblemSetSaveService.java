package com.moplus.moplus_server.domain.problemset.service;

import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemSetSaveService {

    private final ProblemSetRepository problemSetRepository;
    private final ProblemRepository problemRepository;

    @Transactional
    public Long createProblemSet() {

        // ProblemSet 생성
        ProblemSet problemSet = ProblemSet.ofEmptyProblemSet();

        return problemSetRepository.save(problemSet).getId();
    }

}
