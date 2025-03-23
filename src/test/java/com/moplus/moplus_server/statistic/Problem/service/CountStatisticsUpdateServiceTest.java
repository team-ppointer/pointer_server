package com.moplus.moplus_server.statistic.Problem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.moplus.moplus_server.statistic.Problem.domain.ChildProblemStatistic;
import com.moplus.moplus_server.statistic.Problem.domain.ProblemSetStatistic;
import com.moplus.moplus_server.statistic.Problem.domain.ProblemStatistic;
import com.moplus.moplus_server.statistic.Problem.domain.StatisticEntityTarget;
import com.moplus.moplus_server.statistic.Problem.domain.StatisticFieldType;
import com.moplus.moplus_server.statistic.Problem.repository.ChildProblemStatisticRepository;
import com.moplus.moplus_server.statistic.Problem.repository.ProblemSetStatisticRepository;
import com.moplus.moplus_server.statistic.Problem.repository.ProblemStatisticRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CountStatisticsUpdateServiceTest {

    @InjectMocks
    private CountStatisticsUpdateService countStatisticsUpdateService;

    @Mock
    private ProblemStatisticRepository problemStatisticRepository;
    @Mock
    private ProblemSetStatisticRepository problemSetStatisticRepository;
    @Mock
    private ChildProblemStatisticRepository childProblemStatisticRepository;

    @Test
    void 문항_조회수_증가() {
        // given
        Long problemId = 1L;
        ProblemStatistic problemStatistic = new ProblemStatistic(problemId);
        given(problemStatisticRepository.findByIdElseThrow(problemId))
                .willReturn(problemStatistic);

        // when
        countStatisticsUpdateService.updateStatistics(problemId, StatisticFieldType.VIEW,
                StatisticEntityTarget.PROBLEM);

        // then
        verify(problemStatisticRepository).findByIdElseThrow(problemId);
        assertThat(problemStatistic.getViewCount()).isEqualTo(1L);
        assertThat(problemStatistic.getSubmitCount()).isEqualTo(0L);
    }

    @Test
    void 문항세트_풀이수_증가() {
        // given
        Long problemSetId = 1L;
        ProblemSetStatistic problemSetStatistic = new ProblemSetStatistic(problemSetId);
        given(problemSetStatisticRepository.findByIdElseThrow(problemSetId))
                .willReturn(problemSetStatistic);

        // when
        countStatisticsUpdateService.updateStatistics(problemSetId, StatisticFieldType.SUBMIT,
                StatisticEntityTarget.PROBLEM_SET);

        // then
        verify(problemSetStatisticRepository).findByIdElseThrow(problemSetId);
        assertThat(problemSetStatistic.getSubmitCount()).isEqualTo(1L);
        assertThat(problemSetStatistic.getViewCount()).isEqualTo(0L);
    }

    @Test
    void 새끼문항_조회수_증가() {
        // given
        Long childProblemId = 1L;
        ChildProblemStatistic childProblemStatistic = new ChildProblemStatistic(childProblemId);
        given(childProblemStatisticRepository.findByIdElseThrow(childProblemId))
                .willReturn(childProblemStatistic);

        // when
        countStatisticsUpdateService.updateStatistics(childProblemId, StatisticFieldType.VIEW,
                StatisticEntityTarget.CHILD_PROBLEM);

        // then
        verify(childProblemStatisticRepository).findByIdElseThrow(childProblemId);
        assertThat(childProblemStatistic.getViewCount()).isEqualTo(1L);
        assertThat(childProblemStatistic.getSubmitCount()).isEqualTo(0L);
    }
} 