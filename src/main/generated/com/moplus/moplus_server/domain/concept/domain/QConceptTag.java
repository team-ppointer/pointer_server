package com.moplus.moplus_server.domain.concept.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConceptTag is a Querydsl query type for ConceptTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConceptTag extends EntityPathBase<ConceptTag> {

    private static final long serialVersionUID = 652954745L;

    public static final QConceptTag conceptTag = new QConceptTag("conceptTag");

    public final com.moplus.moplus_server.global.common.QBaseEntity _super = new com.moplus.moplus_server.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QConceptTag(String variable) {
        super(ConceptTag.class, forVariable(variable));
    }

    public QConceptTag(Path<? extends ConceptTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConceptTag(PathMetadata metadata) {
        super(ConceptTag.class, metadata);
    }

}

