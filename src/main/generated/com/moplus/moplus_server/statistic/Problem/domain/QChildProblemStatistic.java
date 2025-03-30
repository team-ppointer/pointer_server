package com.moplus.moplus_server.statistic.Problem.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChildProblemStatistic is a Querydsl query type for ChildProblemStatistic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChildProblemStatistic extends EntityPathBase<ChildProblemStatistic> {

    private static final long serialVersionUID = 1136828573L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChildProblemStatistic childProblemStatistic = new QChildProblemStatistic("childProblemStatistic");

    public final NumberPath<Long> childProblemId = createNumber("childProblemId", Long.class);

    public final QCountStatistic countStatistic;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QChildProblemStatistic(String variable) {
        this(ChildProblemStatistic.class, forVariable(variable), INITS);
    }

    public QChildProblemStatistic(Path<? extends ChildProblemStatistic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChildProblemStatistic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChildProblemStatistic(PathMetadata metadata, PathInits inits) {
        this(ChildProblemStatistic.class, metadata, inits);
    }

    public QChildProblemStatistic(Class<? extends ChildProblemStatistic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.countStatistic = inits.isInitialized("countStatistic") ? new QCountStatistic(forProperty("countStatistic")) : null;
    }

}

