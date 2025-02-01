package com.moplus.moplus_server.domain.v0.TestResult.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTestResult is a Querydsl query type for TestResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTestResult extends EntityPathBase<TestResult> {

    private static final long serialVersionUID = -1200752334L;

    public static final QTestResult testResult = new QTestResult("testResult");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> practiceTestId = createNumber("practiceTestId", Long.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final ComparablePath<java.time.Duration> solvingTime = createComparable("solvingTime", java.time.Duration.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QTestResult(String variable) {
        super(TestResult.class, forVariable(variable));
    }

    public QTestResult(Path<? extends TestResult> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestResult(PathMetadata metadata) {
        super(TestResult.class, metadata);
    }

}

