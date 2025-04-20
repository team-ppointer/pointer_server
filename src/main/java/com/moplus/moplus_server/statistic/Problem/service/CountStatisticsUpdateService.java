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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountStatisticsUpdateService {
    private final ProblemStatisticRepository problemStatisticRepository;
    private final ProblemSetStatisticRepository problemSetStatisticRepository;
    private final ChildProblemStatisticRepository childProblemStatisticRepository;

    @Transactional
    public void updateStatistics(Long statisticEntityTargetId, StatisticFieldType type, StatisticEntityTarget target) {
        StatisticCounter statistic = findStatistic(statisticEntityTargetId, target);
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

    private StatisticCounter findStatistic(Long statisticEntityTargetId, StatisticEntityTarget target) {
        try {
            return switch (target) {
                case PROBLEM -> problemStatisticRepository.findByProblemIdElseThrow(statisticEntityTargetId);
                case PROBLEM_SET -> problemSetStatisticRepository.findByProblemSetIdElseThrow(statisticEntityTargetId);
                case CHILD_PROBLEM ->
                        childProblemStatisticRepository.findByChildProblemIdOrElse(statisticEntityTargetId);
            };
        } catch (Exception e) {
            String message = String.format("📌 [통계 조회 실패] targetType=%s, id=%d, error=%s", target,
                    statisticEntityTargetId, e.getMessage());
            log.warn(message, e);
            throw e;
        }
    }
}
