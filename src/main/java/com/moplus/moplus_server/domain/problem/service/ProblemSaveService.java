package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminIdService;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemCustomId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import com.moplus.moplus_server.admin.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.admin.problem.dto.response.ProblemPostResponse;
import com.moplus.moplus_server.domain.problem.repository.PracticeTestTagRepository;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problem.service.mapper.ProblemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemSaveService {

    private final ProblemRepository problemRepository;
    private final PracticeTestTagRepository practiceTestRepository;
    private final ProblemAdminIdService problemAdminIdService;
    private final ProblemMapper problemMapper;

    @Transactional
    public ProblemPostResponse createProblem(ProblemPostRequest request) {
        PracticeTestTag practiceTestTag = getPracticeTestTag(request);
        ProblemCustomId problemCustomId = createProblemCustomId(request);
        Problem problem = createProblem(request, problemCustomId, practiceTestTag);
        
        return ProblemPostResponse.of(problemRepository.save(problem));
    }

    private Problem createProblem(ProblemPostRequest request, ProblemCustomId problemCustomId, PracticeTestTag practiceTestTag) {
        if (request.problemType().isCreationProblem()) {
            return problemMapper.from(request, problemCustomId, practiceTestTag);
        }
        return problemMapper.from(request.problemType(), problemCustomId);
    }

    private ProblemCustomId createProblemCustomId(ProblemPostRequest request) {
        if (request.problemType().requiresPracticeTest()) {
            PracticeTestTag practiceTestTag = practiceTestRepository.findByIdElseThrow(request.practiceTestId());
            return problemAdminIdService.nextId(request.number(), practiceTestTag, request.problemType());
        }
        return problemAdminIdService.nextId(request.problemType());
    }

    private PracticeTestTag getPracticeTestTag(ProblemPostRequest request) {
        if (request.problemType().requiresPracticeTest()) {
            return practiceTestRepository.findByIdElseThrow(request.practiceTestId());
        }
        return null;
    }
}
