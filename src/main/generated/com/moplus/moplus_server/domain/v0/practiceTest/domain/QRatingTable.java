package com.moplus.moplus_server.domain.v0.practiceTest.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRatingTable is a Querydsl query type for RatingTable
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRatingTable extends EntityPathBase<RatingTable> {

    private static final long serialVersionUID = -1985854959L;

    public static final QRatingTable ratingTable = new QRatingTable("ratingTable");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> practiceTestId = createNumber("practiceTestId", Long.class);

    public final StringPath ratingProvider = createString("ratingProvider");

    public final ListPath<RatingRow, SimplePath<RatingRow>> ratingRows = this.<RatingRow, SimplePath<RatingRow>>createList("ratingRows", RatingRow.class, SimplePath.class, PathInits.DIRECT2);

    public final EnumPath<com.moplus.moplus_server.domain.problem.domain.practiceTest.Subject> subject = createEnum("subject", com.moplus.moplus_server.domain.problem.domain.practiceTest.Subject.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QRatingTable(String variable) {
        super(RatingTable.class, forVariable(variable));
    }

    public QRatingTable(Path<? extends RatingTable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRatingTable(PathMetadata metadata) {
        super(RatingTable.class, metadata);
    }

}

