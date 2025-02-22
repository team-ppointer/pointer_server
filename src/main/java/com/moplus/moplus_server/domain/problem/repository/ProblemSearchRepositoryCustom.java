package com.moplus.moplus_server.domain.problem.repository;

import static com.moplus.moplus_server.domain.concept.domain.QConceptTag.conceptTag;
import static com.moplus.moplus_server.domain.problem.domain.childProblem.QChildProblem.childProblem;
import static com.moplus.moplus_server.domain.problem.domain.problem.QProblem.problem;

import com.moplus.moplus_server.domain.problem.dto.response.ConceptTagSearchResponse;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemSearchGetResponse;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemSearchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ProblemSearchGetResponse> search(String problemId, String title, String memo,
                                                 List<Long> conceptTagIds) {
        return queryFactory
                .select(problem.id, problem.problemCustomId.id, problem.title.title,
                        problem.memo, problem.mainProblemImageUrl)
                .from(problem)
                .leftJoin(childProblem).on(childProblem.in(problem.childProblems))
                .leftJoin(conceptTag).on(conceptTag.id.in(problem.conceptTagIds)
                        .or(conceptTag.id.in(childProblem.conceptTagIds)))
                .where(
                        matchProblemId(problemId),
                        matchTitle(title),
                        matchMemo(memo),
                        matchConceptTags(conceptTagIds)
                )
                .transform(GroupBy.groupBy(problem.id).list(
                        Projections.constructor(ProblemSearchGetResponse.class,
                                problem.id,
                                problem.problemCustomId.id,
                                problem.title.title,
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

    public Page<ProblemSearchGetResponse> searchPaging(String problemId, String title, String memo,
                                                       List<Long> conceptTagIds, Pageable pageable) {
        // 쿼리 실행 전 로그 추가
        System.out.println("Page: " + pageable.getPageNumber() + ", Size: " + pageable.getPageSize());

        // 1. 먼저 페이징된 problem IDs 조회
        List<Long> pagedProblemIds = queryFactory
                .select(problem.id)
                .from(problem)
                .leftJoin(childProblem).on(childProblem.in(problem.childProblems))
                .where(
                        matchConceptTags(conceptTagIds),
                        matchProblemId(problemId),
                        matchTitle(title),
                        matchMemo(memo)
                )
                .groupBy(problem.id)  // problemId로 그룹화
                .orderBy(problem.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 2. 조회된 problem들의 상세 정보와 태그 정보 조회
        List<ProblemSearchGetResponse> result = queryFactory
                .from(problem)
                .leftJoin(childProblem).on(childProblem.in(problem.childProblems))
                .leftJoin(conceptTag).on(conceptTag.id.in(problem.conceptTagIds)
                        .or(conceptTag.id.in(childProblem.conceptTagIds)))
                .where(problem.id.in(pagedProblemIds))
                .transform(GroupBy.groupBy(problem.id).list(
                        Projections.constructor(ProblemSearchGetResponse.class,
                                problem.id,
                                problem.problemCustomId.id,
                                problem.title.title,
                                problem.memo,
                                problem.mainProblemImageUrl,
                                GroupBy.set(  // 여기서 set을 사용하여 중복 제거
                                        Projections.constructor(ConceptTagSearchResponse.class,
                                                conceptTag.id,
                                                conceptTag.name
                                        )
                                )
                        )
                ));

        return new PageImpl<>(result, pageable, countSearch(problemId, title, memo, conceptTagIds));
    }

    private long countSearch(String problemId, String title, String memo, List<Long> conceptTagIds) {
        Long count = queryFactory
                .select(problem.id.countDistinct())
                .from(problem)
                .leftJoin(childProblem).on(childProblem.in(problem.childProblems))
                .leftJoin(conceptTag).on(conceptTag.id.in(problem.conceptTagIds)
                        .or(conceptTag.id.in(childProblem.conceptTagIds)))
                .where(
                        matchProblemId(problemId),
                        matchTitle(title),
                        matchMemo2(memo),
                        matchConceptTags(conceptTagIds)
                )
                .fetchOne();

        return count != null ? count : 0L;
    }

    private BooleanExpression matchConceptTags(List<Long> conceptTagIds) {
        if (conceptTagIds == null || conceptTagIds.isEmpty()) {
            return null;
        }
        return problem.conceptTagIds.any().in(conceptTagIds)
                .or(childProblem.conceptTagIds.any().in(conceptTagIds));
    }

    private BooleanExpression matchProblemId(String problemId) {
        if (problemId == null || problemId.isEmpty()) {
            return null;
        }
        return problem.problemCustomId.id.contains(problemId);
    }

    private BooleanExpression matchTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }
        return problem.title.title.contains(title.trim());
    }

    private BooleanExpression matchMemo(String memo) {
        if (memo == null || memo.trim().isEmpty()) {
            return null;
        }
        return problem.memo.contains(memo.trim());
    }

    private BooleanExpression matchMemo2(String memo) {
        if (memo == null || memo.trim().isEmpty()) {
            return null;
        }
        return Expressions.numberTemplate(Double.class,
                "function('match', {0}, concat({1}, '*'))",
                problem.memo,
                memo.trim()
        ).gt(0);
    }

    // 두 컬럼을 동시에 검색할 경우
    private BooleanExpression matchBoth(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return null;
        }
        return Expressions.booleanTemplate(
                "MATCH(problem_custom_id, title) AGAINST({0} IN BOOLEAN MODE)",
                searchTerm.trim()
        );
    }
}
