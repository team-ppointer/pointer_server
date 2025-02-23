package com.moplus.moplus_server.domain.v0.practiceTest.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblemForTest is a Querydsl query type for ProblemForTest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblemForTest extends EntityPathBase<ProblemForTest> {

    private static final long serialVersionUID = 159101820L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProblemForTest problemForTest = new QProblemForTest("problemForTest");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    public final StringPath answer = createString("answer");

    public final EnumPath<AnswerFormat> answerFormat = createEnum("answerFormat", AnswerFormat.class);

    public final StringPath conceptType = createString("conceptType");

    public final NumberPath<Double> correctRate = createNumber("correctRate", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProblemImageForTest image;

    public final NumberPath<Long> incorrectNum = createNumber("incorrectNum", Long.class);

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final QPracticeTest practiceTest;

    public final StringPath problemNumber = createString("problemNumber");

    public final EnumPath<ProblemRating> problemRating = createEnum("problemRating", ProblemRating.class);

    public final StringPath subunit = createString("subunit");

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QProblemForTest(String variable) {
        this(ProblemForTest.class, forVariable(variable), INITS);
    }

    public QProblemForTest(Path<? extends ProblemForTest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProblemForTest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProblemForTest(PathMetadata metadata, PathInits inits) {
        this(ProblemForTest.class, metadata, inits);
    }

    public QProblemForTest(Class<? extends ProblemForTest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.image = inits.isInitialized("image") ? new QProblemImageForTest(forProperty("image")) : null;
        this.practiceTest = inits.isInitialized("practiceTest") ? new QPracticeTest(forProperty("practiceTest")) : null;
    }

}

