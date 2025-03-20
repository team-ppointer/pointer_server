package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.concept.repository.ConceptTagRepository;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminIdService;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemCustomId;
import com.moplus.moplus_server.admin.problem.dto.request.ProblemUpdateRequest;
import com.moplus.moplus_server.admin.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
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
    private final ChildProblemMapper childProblemMapper;
    private final ProblemMapper problemMapper;

    @Transactional
    public ProblemGetResponse updateProblem(Long problemId, ProblemUpdateRequest request) {
        conceptTagRepository.existsByIdElseThrow(request.conceptTagIds());
        Problem problem = problemRepository.findByIdElseThrow(problemId);
        PracticeTestTag practiceTestTag = null;
        ProblemCustomId problemCustomId = null;
        if (request.problemType() != ProblemType.CREATION_PROBLEM) {
            practiceTestTag = practiceTestRepository.findByIdElseThrow(request.practiceTestId());
            problemCustomId = problemAdminIdService.nextId(request.number(), practiceTestTag,
                    request.problemType());
        } else {
            problemCustomId = problemAdminIdService.nextId(request.problemType());
        }

        Problem inputProblem = problemMapper.from(request, problemCustomId, practiceTestTag);
        problem.update(inputProblem);

        List<ChildProblem> childProblems = changeToChildProblems(request);
        problem.updateChildProblem(childProblems);

        return ProblemGetResponse.of(problemRepository.save(problem));
    }

    private List<ChildProblem> changeToChildProblems(ProblemUpdateRequest request) {
        return request.updateChildProblems().stream()
                .map(childProblemMapper::from)
                .toList();
    }
}
