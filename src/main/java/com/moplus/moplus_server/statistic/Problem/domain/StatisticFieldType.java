package com.moplus.moplus_server.statistic.Problem.domain;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StatisticFieldType {
    VIEW(StatisticCounter::addViewCount),
    SUBMIT(StatisticCounter::addSubmitCount);

    private final Consumer<StatisticCounter> countUpdater;

    public void updateCount(StatisticCounter counter) {
        countUpdater.accept(counter);
    }
} 