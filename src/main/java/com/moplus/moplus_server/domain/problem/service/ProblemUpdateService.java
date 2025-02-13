package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.concept.repository.ConceptTagRepository;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminIdService;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.problem.repository.ChildProblemRepository;
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
public class ProblemUpdateService {

    private final ProblemRepository problemRepository;
    private final ProblemAdminIdService problemAdminIdService;
    private final PracticeTestTagRepository practiceTestRepository;
    private final ConceptTagRepository conceptTagRepository;
    private final ChildProblemRepository childProblemRepository;
    private final ChildProblemMapper childProblemMapper;
    private final ProblemMapper problemMapper;

    @Transactional
    public ProblemGetResponse updateProblem(Long problemId, ProblemUpdateRequest request) {
        PracticeTestTag practiceTestTag = practiceTestRepository.findByIdElseThrow(request.practiceTestId());
        conceptTagRepository.existsByIdElseThrow(request.conceptTagIds());
        Problem problem = problemRepository.findByIdElseThrow(problemId);

        ProblemAdminId problemAdminId = problemAdminIdService.nextId(request.number(), practiceTestTag,
                request.problemType());

        Problem inputProblem = problemMapper.from(request, problemAdminId, practiceTestTag);
        problem.update(inputProblem);
        problem.deleteChildProblem(request.deleteChildProblems());

        List<ChildProblem> childProblems = changeToChildProblems(request);
        problem.updateChildProblem(childProblems);

        return ProblemGetResponse.of(problemRepository.save(problem));
    }

    private List<ChildProblem> changeToChildProblems(ProblemUpdateRequest request) {
        return request.updateChildProblems().stream()
                .map(this::getChildProblem)
                .toList();
    }

    private ChildProblem getChildProblem(ChildProblemUpdateRequest updateChildProblem) {
        if (updateChildProblem.id() == null) {
            return childProblemMapper.from(updateChildProblem);
        }
        ChildProblem childProblem = childProblemRepository.findByIdElseThrow(updateChildProblem.id());
        childProblem.update(childProblemMapper.from(updateChildProblem));
        return childProblem;
    }
}
