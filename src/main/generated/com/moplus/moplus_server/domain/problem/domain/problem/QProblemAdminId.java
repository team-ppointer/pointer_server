package com.moplus.moplus_server.domain.problem.domain.problem;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProblemAdminId is a Querydsl query type for ProblemAdminId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProblemAdminId extends BeanPath<ProblemAdminId> {

    private static final long serialVersionUID = 348147768L;

    public static final QProblemAdminId problemAdminId = new QProblemAdminId("problemAdminId");

    public final StringPath id = createString("id");

    public QProblemAdminId(String variable) {
        super(ProblemAdminId.class, forVariable(variable));
    }

    public QProblemAdminId(Path<? extends ProblemAdminId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProblemAdminId(PathMetadata metadata) {
        super(ProblemAdminId.class, metadata);
    }

}

