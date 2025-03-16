package com.moplus.moplus_server.statistic.Problem.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblemSetStatistic is a Querydsl query type for ProblemSetStatistic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblemSetStatistic extends EntityPathBase<ProblemSetStatistic> {

    private static final long serialVersionUID = -1319940803L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProblemSetStatistic problemSetStatistic = new QProblemSetStatistic("problemSetStatistic");

    public final QCountStatistic countStatistic;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> problemSetId = createNumber("problemSetId", Long.class);

    public QProblemSetStatistic(String variable) {
        this(ProblemSetStatistic.class, forVariable(variable), INITS);
    }

    public QProblemSetStatistic(Path<? extends ProblemSetStatistic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProblemSetStatistic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProblemSetStatistic(PathMetadata metadata, PathInits inits) {
        this(ProblemSetStatistic.class, metadata, inits);
    }

    public QProblemSetStatistic(Class<? extends ProblemSetStatistic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.countStatistic = inits.isInitialized("countStatistic") ? new QCountStatistic(forProperty("countStatistic")) : null;
    }

}

