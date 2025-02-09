package com.moplus.moplus_server.domain.problemset.repository;

import static com.moplus.moplus_server.domain.concept.domain.QConceptTag.conceptTag;
import static com.moplus.moplus_server.domain.problem.domain.problem.QProblem.problem;
import static com.moplus.moplus_server.domain.problemset.domain.QProblemSet.problemSet;
import static com.moplus.moplus_server.domain.publish.domain.QPublish.publish;

import com.moplus.moplus_server.domain.problemset.dto.response.ProblemSetSearchGetResponse;
import com.moplus.moplus_server.domain.problemset.dto.response.ProblemThumbnailResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemSetSearchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ProblemSetSearchGetResponse> search(String problemSetTitle, String problemTitle, List<String> conceptTagNames) {
        return queryFactory
                .from(problemSet)
                .leftJoin(problem).on(problem.id.in(problemSet.problemIds)) // 문제 세트 내 포함된 문항과 조인
                .leftJoin(conceptTag).on(conceptTag.id.in(problem.conceptTagIds)) // 문제의 개념 태그 조인
                .leftJoin(publish).on(publish.problemSetId.eq(problemSet.id)) // 문제 세트와 발행 데이터 조인
                .where(
                        containsProblemSetTitle(problemSetTitle),
                        containsProblemTitle(problemTitle),
                        containsConceptTagNames(conceptTagNames)
                )
                .distinct()
                .transform(GroupBy.groupBy(problemSet.id).list(
                        Projections.constructor(ProblemSetSearchGetResponse.class,
                                problemSet.title.value,
                                problemSet.confirmStatus,
                                publish.publishedDate, // 발행되지 않은 경우 null 반환
                                GroupBy.list(
                                        Projections.constructor(ProblemThumbnailResponse.class,
                                                problem.mainProblemImageUrl
                                        )
                                )
                        )
                ));
    }

    private BooleanExpression containsProblemSetTitle(String problemSetTitle) {
        return (problemSetTitle == null || problemSetTitle.isEmpty()) ? null : problemSet.title.value.containsIgnoreCase(problemSetTitle);
    }

    private BooleanExpression containsProblemTitle(String problemTitle) {
        return (problemTitle == null || problemTitle.isEmpty()) ? null : problem.comment.containsIgnoreCase(problemTitle);
    }

    private BooleanExpression containsConceptTagNames(List<String> conceptTagNames) {
        return (conceptTagNames == null || conceptTagNames.isEmpty()) ? null : conceptTag.name.in(conceptTagNames);
    }
}