package com.moplus.moplus_server.domain.problemset.service;

import com.moplus.moplus_server.domain.concept.domain.ConceptTag;
import com.moplus.moplus_server.domain.concept.repository.ConceptTagRepository;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.repository.PracticeTestTagRepository;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.domain.problemset.dto.response.ProblemSummaryResponse;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.domain.publish.domain.Publish;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemSetGetService {

    private final ProblemSetRepository problemSetRepository;
    private final ProblemRepository problemRepository;
    private final PracticeTestTagRepository practiceTestTagRepository;
    private final ConceptTagRepository conceptTagRepository;
    private final PublishRepository publishRepository;

    @Transactional(readOnly = true)
    public ProblemSetGetResponse getProblemSet(Long problemSetId) {

        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);
        LocalDate publishedDate = publishRepository.findByProblemSetId(problemSetId)
                .map(Publish::getPublishedDate)
                .orElse(null);

        List<ProblemSummaryResponse> problemSummaries = new ArrayList<>();
        for (Long problemId : problemSet.getProblemIds()) {
            Problem problem = problemRepository.findByIdElseThrow(problemId);
            PracticeTestTag practiceTestTag = practiceTestTagRepository.findByIdElseThrow(problem.getPracticeTestId());
            List<String> tagNames = conceptTagRepository.findAllByIdsElseThrow(problem.getConceptTagIds())
                    .stream()
                    .map(ConceptTag::getName)
                    .toList();
            problemSummaries.add(ProblemSummaryResponse.of(problem, practiceTestTag.getName(), tagNames));
        }
        return ProblemSetGetResponse.of(problemSet, publishedDate, problemSummaries);
    }
}
