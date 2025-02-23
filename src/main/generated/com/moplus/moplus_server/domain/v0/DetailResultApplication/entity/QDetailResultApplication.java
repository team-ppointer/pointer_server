package com.moplus.moplus_server.domain.v0.DetailResultApplication.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDetailResultApplication is a Querydsl query type for DetailResultApplication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDetailResultApplication extends EntityPathBase<DetailResultApplication> {

    private static final long serialVersionUID = 215702330L;

    public static final QDetailResultApplication detailResultApplication = new QDetailResultApplication("detailResultApplication");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Long> testResultId = createNumber("testResultId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QDetailResultApplication(String variable) {
        super(DetailResultApplication.class, forVariable(variable));
    }

    public QDetailResultApplication(Path<? extends DetailResultApplication> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDetailResultApplication(PathMetadata metadata) {
        super(DetailResultApplication.class, metadata);
    }

}

