package com.moplus.moplus_server.statistic.Problem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemStatistic implements StatisticCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_statistic_id")
    private Long id;

    private Long problemId;
    private Long viewCount;
    private Long submitCount;

    @Embedded
    private CountStatistic countStatistic;

    @Builder
    public ProblemStatistic(Long problemId, Long viewCount, Long submitCount) {
        this.problemId = problemId;
        this.viewCount = viewCount;
        this.submitCount = submitCount;
    }

    @Override
    public void addViewCount() {
        this.countStatistic.addViewCount();
    }

    @Override
    public void addSubmitCount() {
        this.countStatistic.addSubmitCount();
    }
}
