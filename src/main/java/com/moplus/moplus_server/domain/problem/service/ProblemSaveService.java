package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminIdService;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemCustomId;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemPostResponse;
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
        PracticeTestTag practiceTestTag = practiceTestRepository.findByIdElseThrow(request.practiceTestId());
        ProblemCustomId problemCustomId = problemAdminIdService.nextId(request.number(), practiceTestTag,
                request.problemType());

        Problem problem = problemMapper.from(request, problemCustomId, practiceTestTag);
        return ProblemPostResponse.of(problemRepository.save(problem));
    }
}
