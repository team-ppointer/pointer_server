package com.moplus.moplus_server.domain.problemset.repository;

import static com.moplus.moplus_server.domain.problem.domain.problem.QProblem.problem;
import static com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus.CONFIRMED;
import static com.moplus.moplus_server.domain.problemset.domain.QProblemSet.problemSet;

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

    public List<ProblemSetSearchGetResponse> search(String problemSetTitle, String problemTitle) {
        return queryFactory
                .from(problemSet)
                .leftJoin(problem).on(problem.id.in(problemSet.problemIds)) // 문제 세트 내 포함된 문항과 조인
                .where(
                        containsProblemSetTitle(problemSetTitle),
                        containsProblemTitle(problemTitle)
                )
                .distinct()
                .transform(GroupBy.groupBy(problemSet.id).list(
                        Projections.constructor(ProblemSetSearchGetResponse.class,
                                problemSet.id,
                                problemSet.title.value,
                                problemSet.confirmStatus,
                                GroupBy.list(
                                        Projections.constructor(ProblemThumbnailResponse.class,
                                                problem.title.title,
                                                problem.memo,
                                                problem.mainProblemImageUrl
                                        )
                                )
                        )
                ));
    }

    public List<ProblemSetSearchGetResponse> confirmSearch(String problemSetTitle, String problemTitle) {
        return queryFactory
                .from(problemSet)
                .leftJoin(problem).on(problem.id.in(problemSet.problemIds)) // 문제 세트 내 포함된 문항과 조인
                .where(
                        problemSet.confirmStatus.eq(CONFIRMED),
                        containsProblemSetTitle(problemSetTitle),
                        containsProblemTitle(problemTitle)
                )
                .distinct()
                .transform(GroupBy.groupBy(problemSet.id).list(
                        Projections.constructor(ProblemSetSearchGetResponse.class,
                                problemSet.id,
                                problemSet.title.value,
                                problemSet.confirmStatus,
                                GroupBy.list(
                                        Projections.constructor(ProblemThumbnailResponse.class,
                                                problem.title.title,
                                                problem.memo,
                                                problem.mainProblemImageUrl
                                        )
                                )
                        )
                ));
    }

    private BooleanExpression containsProblemSetTitle(String problemSetTitle) {
        return (problemSetTitle == null || problemSetTitle.isEmpty()) ? null
                : problemSet.title.value.containsIgnoreCase(problemSetTitle);
    }

    private BooleanExpression containsProblemTitle(String problemTitle) {
        return (problemTitle == null || problemTitle.isEmpty()) ? null : problem.memo.containsIgnoreCase(problemTitle);
    }
}