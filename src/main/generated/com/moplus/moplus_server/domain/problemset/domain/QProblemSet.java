package com.moplus.moplus_server.domain.problemset.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblemSet is a Querydsl query type for ProblemSet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblemSet extends EntityPathBase<ProblemSet> {

    private static final long serialVersionUID = -499971265L;

    public static final QProblemSet problemSet = new QProblemSet("problemSet");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    public final EnumPath<ProblemSetConfirmStatus> confirmStatus = createEnum("confirmStatus", ProblemSetConfirmStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath name = createString("name");

    public final ListPath<com.moplus.moplus_server.domain.problem.domain.problem.ProblemId, com.moplus.moplus_server.domain.problem.domain.problem.QProblemId> problemIds = this.<com.moplus.moplus_server.domain.problem.domain.problem.ProblemId, com.moplus.moplus_server.domain.problem.domain.problem.QProblemId>createList("problemIds", com.moplus.moplus_server.domain.problem.domain.problem.ProblemId.class, com.moplus.moplus_server.domain.problem.domain.problem.QProblemId.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QProblemSet(String variable) {
        super(ProblemSet.class, forVariable(variable));
    }

    public QProblemSet(Path<? extends ProblemSet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProblemSet(PathMetadata metadata) {
        super(ProblemSet.class, metadata);
    }

}

