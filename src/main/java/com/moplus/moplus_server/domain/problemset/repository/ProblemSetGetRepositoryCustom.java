package com.moplus.moplus_server.domain.problemset.repository;

import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSummaryResponse;
import com.moplus.moplus_server.admin.publish.domain.QPublish;
import com.moplus.moplus_server.domain.concept.domain.QConceptTag;
import com.moplus.moplus_server.domain.problem.domain.problem.QProblem;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProblemSetGetRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProblemSetGetResponse getProblemSet(ProblemSet problemSet) {
        // 발행 날짜 조회 쿼리
        List<LocalDate> publishedDates = queryFactory
                .select(QPublish.publish.publishedDate)
                .from(QPublish.publish)
                .where(QPublish.publish.problemSetId.eq(problemSet.getId()))
                .fetch();

        // 문제 조회 쿼리 (문제 자체 정보만 조회)
        List<Tuple> problemData = queryFactory
                .select(
                        QProblem.problem.id,
                        QProblem.problem.problemCustomId,
                        QProblem.problem.title,
                        QProblem.problem.memo,
                        QProblem.problem.mainProblemImageUrl
                )
                .from(QProblem.problem)
                .where(QProblem.problem.id.in(problemSet.getProblemIds()))
                .distinct()
                .fetch();

        // 태그 조회 쿼리 (각 문제별 태그만 조회)
        Map<Long, Set<String>> conceptTagMap = queryFactory
                .select(QProblem.problem.id, QConceptTag.conceptTag.name)
                .from(QProblem.problem)
                .leftJoin(QConceptTag.conceptTag)
                .on(QConceptTag.conceptTag.id.in(QProblem.problem.conceptTagIds))
                .where(QProblem.problem.id.in(problemSet.getProblemIds()))
                .fetch()
                .stream()
                .collect(
                        HashMap::new,
                        (map, tuple) -> map
                                .computeIfAbsent(tuple.get(QProblem.problem.id), k -> new HashSet<>())
                                .add(tuple.get(QConceptTag.conceptTag.name)),
                        HashMap::putAll
                );

        // 문제 요약 정보 생성
        List<ProblemSummaryResponse> problemSummaries = problemData.stream()
                .map(tuple -> ProblemSummaryResponse.builder()
                        .problemId(tuple.get(QProblem.problem.id))
                        .problemCustomId(tuple.get(QProblem.problem.problemCustomId).toString())
                        .problemTitle(tuple.get(QProblem.problem.title).toString())
                        .memo(tuple.get(QProblem.problem.memo))
                        .mainProblemImageUrl(tuple.get(QProblem.problem.mainProblemImageUrl))
                        .tagNames(conceptTagMap.getOrDefault(tuple.get(QProblem.problem.id), new HashSet<>()))
                        .build()
                )
                .toList();

        return ProblemSetGetResponse.of(problemSet, publishedDates, problemSummaries);
    }
}