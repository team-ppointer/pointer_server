package com.moplus.moplus_server.domain.v0.TestResult.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIncorrectProblem is a Querydsl query type for IncorrectProblem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIncorrectProblem extends EntityPathBase<IncorrectProblem> {

    private static final long serialVersionUID = 1019670237L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIncorrectProblem incorrectProblem = new QIncorrectProblem("incorrectProblem");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    public final NumberPath<Double> correctRate = createNumber("correctRate", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath incorrectAnswer = createString("incorrectAnswer");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final NumberPath<Long> practiceTestId = createNumber("practiceTestId", Long.class);

    public final NumberPath<Long> problemId = createNumber("problemCustomId", Long.class);

    public final StringPath problemNumber = createString("problemNumber");

    public final QTestResult testResult;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QIncorrectProblem(String variable) {
        this(IncorrectProblem.class, forVariable(variable), INITS);
    }

    public QIncorrectProblem(Path<? extends IncorrectProblem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIncorrectProblem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIncorrectProblem(PathMetadata metadata, PathInits inits) {
        this(IncorrectProblem.class, metadata, inits);
    }

    public QIncorrectProblem(Class<? extends IncorrectProblem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.testResult = inits.isInitialized("testResult") ? new QTestResult(forProperty("testResult")) : null;
    }

}

