package com.moplus.moplus_server.client.submit.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProblemSubmit is a Querydsl query type for ProblemSubmit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblemSubmit extends EntityPathBase<ProblemSubmit> {

    private static final long serialVersionUID = 1682818189L;

    public static final QProblemSubmit problemSubmit = new QProblemSubmit("problemSubmit");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> problemId = createNumber("problemId", Long.class);

    public final NumberPath<Long> publishId = createNumber("publishId", Long.class);

    public final EnumPath<ProblemSubmitStatus> status = createEnum("status", ProblemSubmitStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QProblemSubmit(String variable) {
        super(ProblemSubmit.class, forVariable(variable));
    }

    public QProblemSubmit(Path<? extends ProblemSubmit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProblemSubmit(PathMetadata metadata) {
        super(ProblemSubmit.class, metadata);
    }

}

