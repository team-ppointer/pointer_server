package com.moplus.moplus_server.client.submit.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChildProblemSubmit is a Querydsl query type for ChildProblemSubmit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChildProblemSubmit extends EntityPathBase<ChildProblemSubmit> {

    private static final long serialVersionUID = -1656142683L;

    public static final QChildProblemSubmit childProblemSubmit = new QChildProblemSubmit("childProblemSubmit");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    public final NumberPath<Long> childProblemId = createNumber("childProblemId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> publishId = createNumber("publishId", Long.class);

    public final EnumPath<ChildProblemSubmitStatus> status = createEnum("status", ChildProblemSubmitStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QChildProblemSubmit(String variable) {
        super(ChildProblemSubmit.class, forVariable(variable));
    }

    public QChildProblemSubmit(Path<? extends ChildProblemSubmit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChildProblemSubmit(PathMetadata metadata) {
        super(ChildProblemSubmit.class, metadata);
    }

}

