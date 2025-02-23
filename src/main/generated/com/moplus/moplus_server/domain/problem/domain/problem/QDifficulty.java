package com.moplus.moplus_server.domain.problem.domain.problem;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDifficulty is a Querydsl query type for Difficulty
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDifficulty extends BeanPath<Difficulty> {

    private static final long serialVersionUID = 175172936L;

    public static final QDifficulty difficulty1 = new QDifficulty("difficulty1");

    public final NumberPath<Integer> difficulty = createNumber("difficulty", Integer.class);

    public QDifficulty(String variable) {
        super(Difficulty.class, forVariable(variable));
    }

    public QDifficulty(Path<? extends Difficulty> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDifficulty(PathMetadata metadata) {
        super(Difficulty.class, metadata);
    }

}

