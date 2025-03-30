package com.moplus.moplus_server.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOauthInfo is a Querydsl query type for OauthInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOauthInfo extends BeanPath<OauthInfo> {

    private static final long serialVersionUID = 1483708866L;

    public static final QOauthInfo oauthInfo = new QOauthInfo("oauthInfo");

    public final StringPath name = createString("name");

    public final StringPath oauthEmail = createString("oauthEmail");

    public final StringPath oauthId = createString("oauthId");

    public final StringPath oauthProvider = createString("oauthProvider");

    public QOauthInfo(String variable) {
        super(OauthInfo.class, forVariable(variable));
    }

    public QOauthInfo(Path<? extends OauthInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOauthInfo(PathMetadata metadata) {
        super(OauthInfo.class, metadata);
    }

}

