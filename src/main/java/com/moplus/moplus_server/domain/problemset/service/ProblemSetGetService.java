package com.moplus.moplus_server.domain.problemset.service;

import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSummaryResponse;
import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetGetRepositoryCustom;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemSetGetService {

    private final ProblemSetRepository problemSetRepository;
    private final PublishRepository publishRepository;
    private final ProblemSetGetRepositoryCustom problemSetGetRepositoryCustom;

    @Transactional(readOnly = true)
    public ProblemSetGetResponse getProblemSet(Long problemSetId) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);

        List<LocalDate> publishedDates = publishRepository.findByProblemSetId(problemSetId).stream()
                .map(Publish::getPublishedDate)
                .toList();
        List<Long> problemIds = problemSet.getProblemIds();
        List<ProblemSummaryResponse> fetched = problemSetGetRepositoryCustom.findProblemSummariesWithTags(problemIds);

        // 순서 재정렬
        Map<Long, ProblemSummaryResponse> mapped = fetched.stream()
                .collect(java.util.stream.Collectors.toMap(ProblemSummaryResponse::getProblemId, p -> p));

        List<ProblemSummaryResponse> orderedSummaries = problemIds.stream()
                .map(mapped::get)
                .filter(java.util.Objects::nonNull)
                .toList();

        return ProblemSetGetResponse.of(problemSet, publishedDates, orderedSummaries);
    }

    @Transactional(readOnly = true)
    public List<ProblemSetGetResponse> getProblemSets(List<Long> problemSetIds) {
        return problemSetIds.stream()
                .map(this::getProblemSet)
                .toList();
    }
}
