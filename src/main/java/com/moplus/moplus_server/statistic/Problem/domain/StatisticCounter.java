package com.moplus.moplus_server.statistic.Problem.domain;

public interface StatisticCounter {
    void addSubmitCount();

    void addViewCount();

    default void updateCount(StatisticFieldType type) {
        type.updateCount(this);
    }
}
