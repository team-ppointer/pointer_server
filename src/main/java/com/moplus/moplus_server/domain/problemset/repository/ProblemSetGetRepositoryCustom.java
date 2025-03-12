package com.moplus.moplus_server.domain.problemset.repository;

import static com.moplus.moplus_server.admin.publish.domain.QPublish.*;
import static com.moplus.moplus_server.domain.concept.domain.QConceptTag.*;
import static com.moplus.moplus_server.domain.problem.domain.problem.QProblem.*;

import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSummaryResponse;
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
                .select(publish.publishedDate)
                .from(publish)
                .where(publish.problemSetId.eq(problemSet.getId()))
                .fetch();

        // 문제 조회 쿼리 (문제 자체 정보만 조회)
        List<Tuple> problemData = queryFactory
                .select(
                        problem.id,
                        problem.problemCustomId.id,
                        problem.title.title,
                        problem.memo,
                        problem.mainProblemImageUrl
                )
                .from(problem)
                .where(problem.id.in(problemSet.getProblemIds()))
                .distinct()
                .fetch();

        // 태그 조회 쿼리 (각 문제별 태그만 조회)
        Map<Long, Set<String>> conceptTagMap = queryFactory
                .select(problem.id, conceptTag.name)
                .from(problem)
                .leftJoin(conceptTag)
                .on(conceptTag.id.in(problem.conceptTagIds))
                .where(problem.id.in(problemSet.getProblemIds()))
                .fetch()
                .stream()
                .collect(
                        HashMap::new,
                        (map, tuple) -> map
                                .computeIfAbsent(tuple.get(problem.id), k -> new HashSet<>())
                                .add(tuple.get(conceptTag.name)),
                        HashMap::putAll
                );

        // 문제 요약 정보 생성
        List<ProblemSummaryResponse> problemSummaries = problemData.stream()
                .map(tuple -> ProblemSummaryResponse.builder()
                        .problemId(tuple.get(problem.id))
                        .problemCustomId(tuple.get(problem.problemCustomId.id))
                        .problemTitle(tuple.get(problem.title.title))
                        .memo(tuple.get(problem.memo))
                        .mainProblemImageUrl(tuple.get(problem.mainProblemImageUrl))
                        .tagNames(conceptTagMap.getOrDefault(tuple.get(problem.id), new HashSet<>()))
                        .build()
                )
                .toList();

        return ProblemSetGetResponse.of(problemSet, publishedDates, problemSummaries);
    }
}