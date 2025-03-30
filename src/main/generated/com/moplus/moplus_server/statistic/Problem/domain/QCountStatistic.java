package com.moplus.moplus_server.statistic.Problem.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCountStatistic is a Querydsl query type for CountStatistic
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCountStatistic extends BeanPath<CountStatistic> {

    private static final long serialVersionUID = 1047466257L;

    public static final QCountStatistic countStatistic = new QCountStatistic("countStatistic");

    public final NumberPath<Long> submitCount = createNumber("submitCount", Long.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QCountStatistic(String variable) {
        super(CountStatistic.class, forVariable(variable));
    }

    public QCountStatistic(Path<? extends CountStatistic> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCountStatistic(PathMetadata metadata) {
        super(CountStatistic.class, metadata);
    }

}

