package com.moplus.moplus_server.domain.problem.domain.problem;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProblemId is a Querydsl query type for ProblemId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProblemId extends BeanPath<ProblemId> {

    private static final long serialVersionUID = -1309260563L;

    public static final QProblemId problemId = new QProblemId("problemId");

    public final StringPath id = createString("id");

    public QProblemId(String variable) {
        super(ProblemId.class, forVariable(variable));
    }

    public QProblemId(Path<? extends ProblemId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProblemId(PathMetadata metadata) {
        super(ProblemId.class, metadata);
    }

}

