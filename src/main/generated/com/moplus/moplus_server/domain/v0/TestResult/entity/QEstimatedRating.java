package com.moplus.moplus_server.domain.v0.TestResult.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEstimatedRating is a Querydsl query type for EstimatedRating
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEstimatedRating extends EntityPathBase<EstimatedRating> {

    private static final long serialVersionUID = -1923088138L;

    public static final QEstimatedRating estimatedRating1 = new QEstimatedRating("estimatedRating1");

    public final NumberPath<Integer> estimatedRating = createNumber("estimatedRating", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ratingProvider = createString("ratingProvider");

    public final NumberPath<Long> testResultId = createNumber("testResultId", Long.class);

    public QEstimatedRating(String variable) {
        super(EstimatedRating.class, forVariable(variable));
    }

    public QEstimatedRating(Path<? extends EstimatedRating> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEstimatedRating(PathMetadata metadata) {
        super(EstimatedRating.class, metadata);
    }

}

