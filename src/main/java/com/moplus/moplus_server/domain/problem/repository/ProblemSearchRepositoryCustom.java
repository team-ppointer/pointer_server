package com.moplus.moplus_server.domain.problem.repository;

import static com.moplus.moplus_server.domain.concept.domain.QConceptTag.conceptTag;
import static com.moplus.moplus_server.domain.problem.domain.childProblem.QChildProblem.childProblem;
import static com.moplus.moplus_server.domain.problem.domain.problem.QProblem.problem;

import com.moplus.moplus_server.domain.problem.dto.response.ConceptTagSearchResponse;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemSearchGetResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemSearchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ProblemSearchGetResponse> search(String problemId, String memo, List<Long> conceptTagIds) {
        return queryFactory
                .select(problem.problemCustomId.id, problem.memo, problem.mainProblemImageUrl)
                .from(problem)
                .leftJoin(childProblem).on(childProblem.in(problem.childProblems))
                .leftJoin(conceptTag).on(conceptTag.id.in(problem.conceptTagIds)
                        .or(conceptTag.id.in(childProblem.conceptTagIds)))
                .where(
                        containsProblemId(problemId),
                        containsName(memo),
                        hasConceptTags(conceptTagIds)
                )
                .distinct()
                .transform(GroupBy.groupBy(problem.id).list(
                        Projections.constructor(ProblemSearchGetResponse.class,
                                problem.id,
                                problem.problemCustomId.id,
                                problem.memo,
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

    private BooleanExpression hasConceptTags(List<Long> conceptTagIds) {
        if (conceptTagIds == null || conceptTagIds.isEmpty()) {
            return null;
        }

        return problem.id.in(
                JPAExpressions
                        .selectFrom(problem)
                        .where(
                                problem.conceptTagIds.any().in(conceptTagIds)
                                        .or(
                                                problem.id.in(
                                                        JPAExpressions
                                                                .select(childProblem.id)
                                                                .from(childProblem)
                                                                .where(childProblem.conceptTagIds.any()
                                                                        .in(conceptTagIds))
                                                )
                                        )
                        )
                        .select(problem.id)
        );
    }

    //problemCustomId 일부 포함 검색
    private BooleanExpression containsProblemId(String problemId) {
        return (problemId == null || problemId.isEmpty()) ? null
                : problem.problemCustomId.id.containsIgnoreCase(problemId);
    }

    //name 조건 (포함 검색)
    private BooleanExpression containsName(String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            return null;
        }
        return problem.memo.containsIgnoreCase(comment.trim());
    }

    //conceptTagIds 조건 (하나라도 포함되면 조회)
    private BooleanExpression inConceptTagIds(List<Long> conceptTagIds) {
        return (conceptTagIds == null || conceptTagIds.isEmpty()) ? null
                : problem.conceptTagIds.any().in(conceptTagIds);
    }
}
