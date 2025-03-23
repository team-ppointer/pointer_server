package com.moplus.moplus_server.statistic.Problem.service;

import com.moplus.moplus_server.statistic.Problem.repository.ProblemSetStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CountStatisticsGetService {

    private final ProblemSetStatisticRepository problemSetStatisticRepository;

    @Transactional(readOnly = true)
    public Long getProblemSetCount(Long id) {
        return problemSetStatisticRepository.findByIdElseThrow(id).getSubmitCount();
    }
}
