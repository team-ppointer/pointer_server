package com.moplus.moplus_server.domain.problemset.repository;

import static com.moplus.moplus_server.domain.concept.domain.QConceptTag.conceptTag;
import static com.moplus.moplus_server.domain.problem.domain.childProblem.QChildProblem.childProblem;
import static com.moplus.moplus_server.domain.problem.domain.problem.QProblem.problem;

import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSummaryResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemSetGetRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<ProblemSummaryResponse> findProblemSummariesWithTags(List<Long> problemIds) {
        return queryFactory
                .from(problem)
                .leftJoin(childProblem).on(childProblem.in(problem.childProblems))
                .leftJoin(conceptTag).on(
                        conceptTag.id.in(problem.conceptTagIds)
                                .or(conceptTag.id.in(childProblem.conceptTagIds))
                )
                .where(problem.id.in(problemIds))
                .distinct()
                .transform(GroupBy.groupBy(problem.id).list(
                        Projections.constructor(ProblemSummaryResponse.class,
                                problem,
                                GroupBy.set(conceptTag.name)
                        )
                ));
    }
}
