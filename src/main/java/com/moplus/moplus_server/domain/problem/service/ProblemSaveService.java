package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.concept.repository.ConceptTagRepository;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemIdService;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.repository.PracticeTestTagRepository;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problem.service.mapper.ChildProblemMapper;
import com.moplus.moplus_server.domain.problem.service.mapper.ProblemMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemSaveService {

    private final ProblemRepository problemRepository;
    private final PracticeTestTagRepository practiceTestRepository;
    private final ConceptTagRepository conceptTagRepository;
    private final ProblemIdService problemIdService;
    private final ProblemMapper problemMapper;
    private final ChildProblemMapper childProblemMapper;

    @Transactional
    public ProblemId createProblem(ProblemPostRequest request) {
        PracticeTestTag practiceTestTag = practiceTestRepository.findByIdElseThrow(request.practiceTestId());
        problemRepository.existsByPracticeTestIdAndNumberOrThrow(practiceTestTag.getId(), request.number());
        conceptTagRepository.existsByIdElseThrow(request.conceptTagIds());

        ProblemId problemId = problemIdService.nextId(request.number(), practiceTestTag);
        Problem problem = problemMapper.from(request, problemId, practiceTestTag);

        List<ChildProblem> childProblems = request.childProblems().stream()
                .map(childProblemMapper::from)
                .toList();
        problem.addChildProblem(childProblems);

        return problemRepository.save(problem).getId();
    }
}
