package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.problem.dto.response.ProblemSearchGetResponse;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemSearchResponse;
import com.moplus.moplus_server.domain.problem.repository.ProblemSearchRepositoryCustom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemSearchService {
    private final ProblemSearchRepositoryCustom problemSearchRepository;

    public List<ProblemSearchGetResponse> searchProblems(String problemId, String title, String memo,
                                                         List<Long> conceptTagIds) {
        return problemSearchRepository.search(problemId, title, memo, conceptTagIds);
    }

    public ProblemSearchResponse searchProblemsWithPaging(String problemId, String title, String memo,
                                                          List<Long> conceptTagIds, Pageable pageable) {
        Page<ProblemSearchGetResponse> problemPage = problemSearchRepository.searchPaging(
                problemId, title, memo, conceptTagIds, pageable);

        return ProblemSearchResponse.of(
                problemPage.getContent(),
                problemPage.getTotalElements(),
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
    }
} 