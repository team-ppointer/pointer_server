package com.moplus.moplus_server.domain.v0.practiceTest.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPracticeTest is a Querydsl query type for PracticeTest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPracticeTest extends EntityPathBase<PracticeTest> {

    private static final long serialVersionUID = -700271987L;

    public static final QPracticeTest practiceTest = new QPracticeTest("practiceTest");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    public final ComparablePath<java.time.Duration> averageSolvingTime = createComparable("averageSolvingTime", java.time.Duration.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath provider = createString("provider");

    public final NumberPath<Integer> publicationYear = createNumber("publicationYear", Integer.class);

    public final StringPath round = createString("round");

    public final NumberPath<Integer> solvesCount = createNumber("solvesCount", Integer.class);

    public final EnumPath<com.moplus.moplus_server.domain.problem.domain.practiceTest.Subject> subject = createEnum("subject", com.moplus.moplus_server.domain.problem.domain.practiceTest.Subject.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QPracticeTest(String variable) {
        super(PracticeTest.class, forVariable(variable));
    }

    public QPracticeTest(Path<? extends PracticeTest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPracticeTest(PathMetadata metadata) {
        super(PracticeTest.class, metadata);
    }

}

