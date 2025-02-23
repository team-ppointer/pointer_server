package com.moplus.moplus_server.domain.problem.domain.problem;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProblemCustomId is a Querydsl query type for ProblemCustomId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProblemCustomId extends BeanPath<ProblemCustomId> {

    private static final long serialVersionUID = -517009730L;

    public static final QProblemCustomId problemCustomId = new QProblemCustomId("problemCustomId");

    public final StringPath id = createString("id");

    public QProblemCustomId(String variable) {
        super(ProblemCustomId.class, forVariable(variable));
    }

    public QProblemCustomId(Path<? extends ProblemCustomId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProblemCustomId(PathMetadata metadata) {
        super(ProblemCustomId.class, metadata);
    }

}

