package com.moplus.moplus_server.statistic.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIncorrectConceptTagStatistics is a Querydsl query type for IncorrectConceptTagStatistics
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIncorrectConceptTagStatistics extends EntityPathBase<IncorrectConceptTagStatistics> {

    private static final long serialVersionUID = 1288752209L;

    public static final QIncorrectConceptTagStatistics incorrectConceptTagStatistics = new QIncorrectConceptTagStatistics("incorrectConceptTagStatistics");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    public final NumberPath<Long> conceptTagId = createNumber("conceptTagId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> incorrectCount = createNumber("incorrectCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QIncorrectConceptTagStatistics(String variable) {
        super(IncorrectConceptTagStatistics.class, forVariable(variable));
    }

    public QIncorrectConceptTagStatistics(Path<? extends IncorrectConceptTagStatistics> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIncorrectConceptTagStatistics(PathMetadata metadata) {
        super(IncorrectConceptTagStatistics.class, metadata);
    }

}

