package com.moplus.moplus_server.domain.problemset.repository;

import static com.moplus.moplus_server.domain.concept.domain.QConceptTag.conceptTag;
import static com.moplus.moplus_server.domain.problem.domain.problem.QProblem.problem;
import static com.moplus.moplus_server.domain.problemset.domain.QProblemSet.problemSet;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problemset.dto.response.ProblemSetSearchGetResponse;
import com.moplus.moplus_server.domain.problemset.dto.response.ProblemThumbnailResponse;
import com.moplus.moplus_server.domain.problemset.domain.QProblemSet;
import com.moplus.moplus_server.domain.problem.domain.problem.QProblem;
import com.moplus.moplus_server.domain.concept.domain.QConceptTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemSetSearchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ProblemSetSearchGetResponse> search(String problemSetTitle, String problemTitle, List<String> conceptTagNames) {

        return queryFactory
                .select(problemSet)
                .from(problemSet)
                .leftJoin(problem).on(problem.id.in(problemSet.problemIds))
                .leftJoin(conceptTag).on(conceptTag.id.in(problem.conceptTagIds))
                .where(
                        problemSetTitleContains(problemSetTitle),
                        problemTitleContains(problemTitle),
                        conceptTagNamesIn(conceptTagNames)
                )
                .fetch()
                .stream()
                .map(ps -> new ProblemSetSearchGetResponse(
                        ps.getTitle(),
                        ps.getProblemIds().stream()
                                .map(pid -> {
                                    Problem p = queryFactory
                                            .select(problem)
                                            .from(problem)
                                            .where(problem.id.eq(pid))
                                            .fetchOne();
                                    return new ProblemThumbnailResponse(
                                            p != null ? p.getMainProblemImageUrl() : null,
                                            p != null ? p.getConceptTagIds().stream()
                                                    .map(tagId -> queryFactory
                                                            .select(conceptTag.name)
                                                            .from(conceptTag)
                                                            .where(conceptTag.id.eq(tagId))
                                                            .fetchOne())
                                                    .collect(Collectors.toList())
                                                    : List.of()
                                    );
                                })
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    private BooleanExpression problemSetTitleContains(String problemSetTitle) {
        return problemSetTitle != null ? QProblemSet.problemSet.title.contains(problemSetTitle) : null;
    }

    private BooleanExpression problemTitleContains(String problemTitle) {
        return problemTitle != null ? QProblem.problem.comment.contains(problemTitle) : null;
    }

    private BooleanExpression conceptTagNamesIn(List<String> conceptTagNames) {
        return (conceptTagNames != null && !conceptTagNames.isEmpty()) ?
                QConceptTag.conceptTag.name.in(conceptTagNames) : null;
    }
}