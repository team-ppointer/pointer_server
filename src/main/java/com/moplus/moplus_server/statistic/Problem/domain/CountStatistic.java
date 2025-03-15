package com.moplus.moplus_server.statistic.Problem.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class CountStatistic implements StatisticCounter {
    private Long viewCount;
    private Long submitCount;

    public CountStatistic() {
        this.viewCount = 0L;
        this.submitCount = 0L;
    }

    @Override
    public void addViewCount() {
        this.viewCount++;
    }

    @Override
    public void addSubmitCount() {
        this.submitCount++;
    }
}
