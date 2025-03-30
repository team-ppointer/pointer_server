package com.moplus.moplus_server.statistic.Problem.service;

import com.moplus.moplus_server.statistic.Problem.domain.StatisticCounter;
import com.moplus.moplus_server.statistic.Problem.domain.StatisticEntityTarget;
import com.moplus.moplus_server.statistic.Problem.domain.StatisticFieldType;
import com.moplus.moplus_server.statistic.Problem.repository.ChildProblemStatisticRepository;
import com.moplus.moplus_server.statistic.Problem.repository.ProblemSetStatisticRepository;
import com.moplus.moplus_server.statistic.Problem.repository.ProblemStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CountStatisticsUpdateService {
    private final ProblemStatisticRepository problemStatisticRepository;
    private final ProblemSetStatisticRepository problemSetStatisticRepository;
    private final ChildProblemStatisticRepository childProblemStatisticRepository;

    @Transactional
    public void updateStatistics(Long id, StatisticFieldType type, StatisticEntityTarget target) {
        StatisticCounter statistic = findStatistic(id, target);
        statistic.updateCount(type);
    }

    private StatisticCounter findStatistic(Long id, StatisticEntityTarget target) {
        return switch (target) {
            case PROBLEM -> problemStatisticRepository.findByIdElseThrow(id);
            case PROBLEM_SET -> problemSetStatisticRepository.findByIdElseThrow(id);
            case CHILD_PROBLEM -> childProblemStatisticRepository.findByIdElseThrow(id);
        };
    }
}
