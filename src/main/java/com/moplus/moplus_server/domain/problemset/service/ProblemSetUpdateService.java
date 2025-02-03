package com.moplus.moplus_server.domain.problemset.service;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemReorderRequest;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemSetUpdateRequest;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemSetUpdateService {

    private final ProblemSetRepository problemSetRepository;
    private final ProblemRepository problemRepository;

    @Transactional
    public void reorderProblems(Long problemSetId, ProblemReorderRequest request) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);

        // 기존 문항 ID 리스트 업데이트 (순서 반영)
        List<ProblemId> updatedProblemIds = request.newProblems().stream()
                .map(ProblemId::new)
                .collect(Collectors.toList());

        problemSet.updateProblemOrder(updatedProblemIds);
    }

    @Transactional
    public void updateProblemSet(Long problemSetId, ProblemSetUpdateRequest request) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);

        // 문항 리스트 검증
        List<ProblemId> problemIdList = request.problems().stream()
                .map(ProblemId::new)
                .collect(Collectors.toList());
        problemIdList.forEach(problemRepository::findByIdElseThrow);

        problemSet.updateProblemSet(request.problemSetName(), problemIdList);
    }

    @Transactional
    public ProblemSetConfirmStatus toggleConfirmProblemSet(Long problemSetId) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);
        List<Problem> problems = new ArrayList<>();
        for (ProblemId problemId : problemSet.getProblemIds()) {
            Problem problem = problemRepository.findByIdElseThrow(problemId);
            problems.add(problem);
        }
        problemSet.toggleConfirm(problems);
        return problemSet.getConfirmStatus();
    }
}
