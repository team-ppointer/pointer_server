package com.moplus.moplus_server.domain.problem.domain.childProblem;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChildProblem is a Querydsl query type for ChildProblem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChildProblem extends EntityPathBase<ChildProblem> {

    private static final long serialVersionUID = 1030139824L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChildProblem childProblem = new QChildProblem("childProblem");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    public final com.moplus.moplus_server.domain.problem.domain.QAnswer answer;

    public final EnumPath<com.moplus.moplus_server.domain.problem.domain.problem.AnswerType> answerType = createEnum("answerType", com.moplus.moplus_server.domain.problem.domain.problem.AnswerType.class);

    public final SetPath<Long, NumberPath<Long>> conceptTagIds = this.<Long, NumberPath<Long>>createSet("conceptTagIds", Long.class, NumberPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QChildProblem(String variable) {
        this(ChildProblem.class, forVariable(variable), INITS);
    }

    public QChildProblem(Path<? extends ChildProblem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChildProblem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChildProblem(PathMetadata metadata, PathInits inits) {
        this(ChildProblem.class, metadata, inits);
    }

    public QChildProblem(Class<? extends ChildProblem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.answer = inits.isInitialized("answer") ? new com.moplus.moplus_server.domain.problem.domain.QAnswer(forProperty("answer")) : null;
    }

}

