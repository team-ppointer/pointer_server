package com.moplus.moplus_server.statistic.Problem.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblemStatistic is a Querydsl query type for ProblemStatistic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblemStatistic extends EntityPathBase<ProblemStatistic> {

    private static final long serialVersionUID = 843488641L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProblemStatistic problemStatistic = new QProblemStatistic("problemStatistic");

    public final QCountStatistic countStatistic;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> problemId = createNumber("problemId", Long.class);

    public QProblemStatistic(String variable) {
        this(ProblemStatistic.class, forVariable(variable), INITS);
    }

    public QProblemStatistic(Path<? extends ProblemStatistic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProblemStatistic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProblemStatistic(PathMetadata metadata, PathInits inits) {
        this(ProblemStatistic.class, metadata, inits);
    }

    public QProblemStatistic(Class<? extends ProblemStatistic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.countStatistic = inits.isInitialized("countStatistic") ? new QCountStatistic(forProperty("countStatistic")) : null;
    }

}

