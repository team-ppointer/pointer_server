package com.moplus.moplus_server.domain.problem.domain.problem;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblem is a Querydsl query type for Problem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblem extends EntityPathBase<Problem> {

    private static final long serialVersionUID = -1319796686L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProblem problem = new QProblem("problem");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    public final com.moplus.moplus_server.domain.problem.domain.QAnswer answer;

    public final EnumPath<AnswerType> answerType = createEnum("answerType", AnswerType.class);

    public final ListPath<com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem, com.moplus.moplus_server.domain.problem.domain.childProblem.QChildProblem> childProblems = this.<com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem, com.moplus.moplus_server.domain.problem.domain.childProblem.QChildProblem>createList("childProblems", com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem.class, com.moplus.moplus_server.domain.problem.domain.childProblem.QChildProblem.class, PathInits.DIRECT2);

    public final SetPath<Long, NumberPath<Long>> conceptTagIds = this.<Long, NumberPath<Long>>createSet("conceptTagIds", Long.class, NumberPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final QDifficulty difficulty;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isConfirmed = createBoolean("isConfirmed");

    public final StringPath mainAnalysisImageUrl = createString("mainAnalysisImageUrl");

    public final StringPath mainHandwritingExplanationImageUrl = createString("mainHandwritingExplanationImageUrl");

    public final StringPath mainProblemImageUrl = createString("mainProblemImageUrl");

    public final StringPath memo = createString("memo");

    public final NumberPath<Integer> number = createNumber("number", Integer.class);

    public final NumberPath<Long> practiceTestId = createNumber("practiceTestId", Long.class);

    public final ListPath<String, StringPath> prescriptionImageUrls = this.<String, StringPath>createList("prescriptionImageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    public final QProblemAdminId problemAdminId;

    public final EnumPath<ProblemType> problemType = createEnum("problemType", ProblemType.class);

    public final StringPath readingTipImageUrl = createString("readingTipImageUrl");

    public final StringPath seniorTipImageUrl = createString("seniorTipImageUrl");

    public final QTitle title;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QProblem(String variable) {
        this(Problem.class, forVariable(variable), INITS);
    }

    public QProblem(Path<? extends Problem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProblem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProblem(PathMetadata metadata, PathInits inits) {
        this(Problem.class, metadata, inits);
    }

    public QProblem(Class<? extends Problem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.answer = inits.isInitialized("answer") ? new com.moplus.moplus_server.domain.problem.domain.QAnswer(forProperty("answer")) : null;
        this.difficulty = inits.isInitialized("difficulty") ? new QDifficulty(forProperty("difficulty")) : null;
        this.problemAdminId = inits.isInitialized("problemAdminId") ? new QProblemAdminId(forProperty("problemAdminId")) : null;
        this.title = inits.isInitialized("title") ? new QTitle(forProperty("title")) : null;
    }

}

