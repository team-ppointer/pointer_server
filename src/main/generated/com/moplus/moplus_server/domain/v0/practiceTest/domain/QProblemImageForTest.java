package com.moplus.moplus_server.domain.v0.practiceTest.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProblemImageForTest is a Querydsl query type for ProblemImageForTest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblemImageForTest extends EntityPathBase<ProblemImageForTest> {

    private static final long serialVersionUID = 1499588927L;

    public static final QProblemImageForTest problemImageForTest = new QProblemImageForTest("problemImageForTest");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Long> problemId = createNumber("problemId", Long.class);

    public QProblemImageForTest(String variable) {
        super(ProblemImageForTest.class, forVariable(variable));
    }

    public QProblemImageForTest(Path<? extends ProblemImageForTest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProblemImageForTest(PathMetadata metadata) {
        super(ProblemImageForTest.class, metadata);
    }

}

