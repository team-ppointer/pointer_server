package com.moplus.moplus_server.domain.problem.domain.problem;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecommendedTime is a Querydsl query type for RecommendedTime
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRecommendedTime extends BeanPath<RecommendedTime> {

    private static final long serialVersionUID = -1102611877L;

    public static final QRecommendedTime recommendedTime = new QRecommendedTime("recommendedTime");

    public final NumberPath<Integer> minute = createNumber("minute", Integer.class);

    public final NumberPath<Integer> second = createNumber("second", Integer.class);

    public QRecommendedTime(String variable) {
        super(RecommendedTime.class, forVariable(variable));
    }

    public QRecommendedTime(Path<? extends RecommendedTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecommendedTime(PathMetadata metadata) {
        super(RecommendedTime.class, metadata);
    }

}

