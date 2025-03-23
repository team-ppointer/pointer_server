package com.moplus.moplus_server.admin.publish.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPublish is a Querydsl query type for Publish
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPublish extends EntityPathBase<Publish> {

    private static final long serialVersionUID = 1641302032L;

    public static final QPublish publish = new QPublish("publish");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> problemSetId = createNumber("problemSetId", Long.class);

    public final DatePath<java.time.LocalDate> publishedDate = createDate("publishedDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QPublish(String variable) {
        super(Publish.class, forVariable(variable));
    }

    public QPublish(Path<? extends Publish> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPublish(PathMetadata metadata) {
        super(Publish.class, metadata);
    }

}

