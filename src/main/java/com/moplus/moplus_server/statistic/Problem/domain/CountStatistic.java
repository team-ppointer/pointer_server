package com.moplus.moplus_server.statistic.Problem.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class CountStatistic {
    private Long viewCount;
    private Long submitCount;

    public CountStatistic() {
        this.viewCount = 0L;
        this.submitCount = 0L;
    }
}
