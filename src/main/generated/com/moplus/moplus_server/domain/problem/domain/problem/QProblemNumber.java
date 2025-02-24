package com.moplus.moplus_server.domain.problem.domain.problem;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProblemNumber is a Querydsl query type for ProblemNumber
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProblemNumber extends BeanPath<ProblemNumber> {

    private static final long serialVersionUID = -1679107557L;

    public static final QProblemNumber problemNumber = new QProblemNumber("problemNumber");

    public QProblemNumber(String variable) {
        super(ProblemNumber.class, forVariable(variable));
    }

    public QProblemNumber(Path<? extends ProblemNumber> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProblemNumber(PathMetadata metadata) {
        super(ProblemNumber.class, metadata);
    }

}

