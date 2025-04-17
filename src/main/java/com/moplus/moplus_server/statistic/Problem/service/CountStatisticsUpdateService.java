package com.moplus.moplus_server.statistic.Problem.service;

import com.moplus.moplus_server.statistic.Problem.domain.ChildProblemStatistic;
import com.moplus.moplus_server.statistic.Problem.domain.ProblemSetStatistic;
import com.moplus.moplus_server.statistic.Problem.domain.ProblemStatistic;
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
    public void updateStatistics(Long statisticId, StatisticFieldType type, StatisticEntityTarget target) {
        StatisticCounter statistic = findStatistic(statisticId, target);
        statistic.updateCount(type);
    }

    @Transactional
    public void createStatistics(Long statisticId, StatisticEntityTarget target) {
        switch (target) {
            case PROBLEM -> problemStatisticRepository.saveAndFlush(new ProblemStatistic(statisticId));
            case PROBLEM_SET -> problemSetStatisticRepository.saveAndFlush(new ProblemSetStatistic(statisticId));
            case CHILD_PROBLEM -> childProblemStatisticRepository.saveAndFlush(new ChildProblemStatistic(statisticId));
        }
    }

    private StatisticCounter findStatistic(Long statisticId, StatisticEntityTarget target) {
        return switch (target) {
            case PROBLEM -> problemStatisticRepository.findByIdElseThrow(statisticId);
            case PROBLEM_SET -> problemSetStatisticRepository.findByIdElseThrow(statisticId);
            case CHILD_PROBLEM -> childProblemStatisticRepository.findByIdElseThrow(statisticId);
        };
    }
}
