package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemIdService;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
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
    private final ProblemIdService problemIdService;
    private final ProblemMapper problemMapper;

    @Transactional
    public ProblemAdminId createProblem(ProblemPostRequest request) {
        PracticeTestTag practiceTestTag = practiceTestRepository.findByIdElseThrow(request.practiceTestId());

        ProblemAdminId problemAdminId = problemIdService.nextId(request.number(), practiceTestTag,
                request.problemType());
        Problem problem = problemMapper.from(request, problemAdminId, practiceTestTag);

        return problemRepository.save(problem).getProblemAdminId();
    }
}
