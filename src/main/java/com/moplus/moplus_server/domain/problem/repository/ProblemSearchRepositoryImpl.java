package com.moplus.moplus_server.domain.problem.repository;

import static com.moplus.moplus_server.domain.concept.domain.QConceptTag.conceptTag;
import static com.moplus.moplus_server.domain.problem.domain.problem.QProblem.problem;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.dto.response.ConceptTagSearchResponse;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemSearchGetResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemSearchRepositoryImpl extends QuerydslRepositorySupport implements ProblemSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ProblemSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Problem.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ProblemSearchGetResponse> search(String problemId, String comment, List<Long> conceptTagIds) {
        return queryFactory
                .from(problem)
                .where(
                        containsProblemId(problemId),
                        containsName(comment),
                        inConceptTagIds(conceptTagIds)
                )
                .leftJoin(conceptTag).on(conceptTag.id.in(problem.conceptTagIds))
                .distinct()
                .transform(GroupBy.groupBy(problem.id.id).list(
                        Projections.constructor(ProblemSearchGetResponse.class,
                                problem.id.id,
                                problem.comment,
                                problem.mainProblemImageUrl,
                                GroupBy.set(
                                        Projections.constructor(ConceptTagSearchResponse.class,
                                                conceptTag.id,
                                                conceptTag.name
                                        )
                                )
                        )
                ));
    }

    //problemId 일부 포함 검색
    private BooleanExpression containsProblemId(String problemId) {
        return (problemId == null || problemId.isEmpty()) ? null : problem.id.id.containsIgnoreCase(problemId);
    }

    //name 조건 (포함 검색)
    private BooleanExpression containsName(String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            return null;
        }
        return problem.comment.containsIgnoreCase(comment.trim());
    }

    //conceptTagIds 조건 (하나라도 포함되면 조회)
    private BooleanExpression inConceptTagIds(List<Long> conceptTagIds) {
        return (conceptTagIds == null || conceptTagIds.isEmpty()) ? null
                : problem.conceptTagIds.any().in(conceptTagIds);
    }
}
