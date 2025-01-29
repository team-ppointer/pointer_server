package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.concept.repository.ConceptTagRepository;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTest;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemIdService;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemDeleteRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.problem.repository.PracticeTestTagRepository;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
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

    @Transactional
    public ProblemId createProblem(ProblemPostRequest request) {
        PracticeTest practiceTest = practiceTestRepository.findByIdElseThrow(request.practiceTestId());
        problemRepository.existsByPracticeTestIdAndNumberOrThrow(practiceTest.getId(), request.number());
        conceptTagRepository.existsByIdElseThrow(request.conceptTagIds());

        ProblemId problemId = problemIdService.nextId(request.number(), practiceTest);
        Problem problem = request.toEntity(practiceTest, problemId);
        request.childProblems()
                .forEach(problem::addChildProblem);

        return problemRepository.save(problem).getId();
    }

    @Transactional
    public ProblemGetResponse updateProblem(String problemId, ProblemUpdateRequest request) {
        PracticeTest practiceTest = practiceTestRepository.findByIdElseThrow(request.practiceTestId());
        problemRepository.existsByPracticeTestIdAndNumberOrThrow(practiceTest.getId(), request.number());
        conceptTagRepository.existsByIdElseThrow(request.conceptTagIds());

        Problem problem = problemRepository.findByIdElseThrow(new ProblemId(problemId));
        request.deleteChildProblems().stream()
                .map(ChildProblemDeleteRequest::childProblemId)
                .forEach(problem::deleteChildProblem);
        request.createChildProblems().forEach(problem::addChildProblem);
        request.updateChildProblems().forEach(problem::updateChildProblem);

        Problem savedProblem = problemRepository.save(problem);
        return ProblemGetResponse.of(savedProblem);
    }
}
