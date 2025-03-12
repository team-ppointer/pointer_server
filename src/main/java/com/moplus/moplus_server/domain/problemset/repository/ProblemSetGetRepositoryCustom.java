package com.moplus.moplus_server.domain.problemset.repository;

import static com.moplus.moplus_server.admin.publish.domain.QPublish.*;
import static com.moplus.moplus_server.domain.concept.domain.QConceptTag.*;
import static com.moplus.moplus_server.domain.problem.domain.problem.QProblem.*;
import static com.moplus.moplus_server.domain.problem.domain.childProblem.QChildProblem.*;

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

        // 문항 및 새끼문항 조회
        List<Tuple> problemData = queryFactory
                .select(
                        problem.id,
                        problem.problemCustomId.id,
                        problem.title.title,
                        problem.memo,
                        problem.mainProblemImageUrl
                )
                .from(problem)
                .leftJoin(problem.childProblems, childProblem)  // 자식 문제 JOIN
                .where(problem.id.in(problemSet.getProblemIds()))
                .distinct()
                .fetch();

        // 문항 및 새끼 문항의 개념 태그 조회
        List<Long> allProblemIds = problemSet.getProblemIds(); // 문제 ID 목록
        List<Long> childProblemIds = queryFactory
                .select(childProblem.id)
                .from(problem)
                .join(problem.childProblems, childProblem)
                .where(problem.id.in(allProblemIds))
                .fetch();

        Set<Long> allProblemAndChildProblemIds = new HashSet<>(allProblemIds);
        allProblemAndChildProblemIds.addAll(childProblemIds); // 문제 + 자식 문제 ID 합침

        // 문항 및 자식 문항의 개념 태그 조회
        Map<Long, Set<String>> conceptTagMap = queryFactory
                .select(problem.id, conceptTag.name)
                .from(problem)
                .leftJoin(conceptTag)
                .on(conceptTag.id.in(problem.conceptTagIds))
                .where(problem.id.in(allProblemAndChildProblemIds)) // 문제 + 자식 문제 ID
                .fetch()
                .stream()
                .collect(
                        HashMap::new,
                        (map, tuple) -> map
                                .computeIfAbsent(tuple.get(problem.id), k -> new HashSet<>())
                                .add(tuple.get(conceptTag.name)),
                        HashMap::putAll
                );

        List<ProblemSummaryResponse> problemSummaries = problemData.stream()
                .map(tuple -> ProblemSummaryResponse.builder()
                        .problemId(tuple.get(problem.id))
                        .problemCustomId(tuple.get(problem.problemCustomId.id))
                        .problemTitle(tuple.get(problem.title.title))
                        .memo(tuple.get(problem.memo))
                        .mainProblemImageUrl(tuple.get(problem.mainProblemImageUrl))
                        .tagNames(conceptTagMap.getOrDefault(tuple.get(problem.id), new HashSet<>())) // 태그 매핑
                        .build()
                )
                .toList();

        return ProblemSetGetResponse.of(problemSet, publishedDates, problemSummaries);
    }
}