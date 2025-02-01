package com.moplus.moplus_server.domain.problem.domain.practiceTest;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPracticeTestTag is a Querydsl query type for PracticeTestTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPracticeTestTag extends EntityPathBase<PracticeTestTag> {

    private static final long serialVersionUID = -2120162934L;

    public static final QPracticeTestTag practiceTestTag = new QPracticeTestTag("practiceTestTag");

    public final StringPath area = createString("area");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final StringPath name = createString("name");

    public final EnumPath<Subject> subject = createEnum("subject", Subject.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QPracticeTestTag(String variable) {
        super(PracticeTestTag.class, forVariable(variable));
    }

    public QPracticeTestTag(Path<? extends PracticeTestTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPracticeTestTag(PathMetadata metadata) {
        super(PracticeTestTag.class, metadata);
    }

}

