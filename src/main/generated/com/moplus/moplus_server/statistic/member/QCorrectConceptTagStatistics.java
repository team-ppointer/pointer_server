package com.moplus.moplus_server.statistic.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCorrectConceptTagStatistics is a Querydsl query type for CorrectConceptTagStatistics
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCorrectConceptTagStatistics extends EntityPathBase<CorrectConceptTagStatistics> {

    private static final long serialVersionUID = -313521578L;

    public static final QCorrectConceptTagStatistics correctConceptTagStatistics = new QCorrectConceptTagStatistics("correctConceptTagStatistics");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    public final NumberPath<Long> conceptTagId = createNumber("conceptTagId", Long.class);

    public final NumberPath<Integer> correctCount = createNumber("correctCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QCorrectConceptTagStatistics(String variable) {
        super(CorrectConceptTagStatistics.class, forVariable(variable));
    }

    public QCorrectConceptTagStatistics(Path<? extends CorrectConceptTagStatistics> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCorrectConceptTagStatistics(PathMetadata metadata) {
        super(CorrectConceptTagStatistics.class, metadata);
    }

}

