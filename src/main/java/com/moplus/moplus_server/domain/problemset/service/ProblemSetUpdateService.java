package com.moplus.moplus_server.domain.problemset.service;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
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
        List<ProblemAdminId> updatedProblemAdminIds = request.newProblems().stream()
                .map(ProblemAdminId::new)
                .collect(Collectors.toList());

        problemSet.updateProblemOrder(updatedProblemAdminIds);
    }

    @Transactional
    public void updateProblemSet(Long problemSetId, ProblemSetUpdateRequest request) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);

        // 빈 문항 유효성 검증
        if (request.problems().isEmpty()) {
            throw new InvalidValueException(ErrorCode.EMPTY_PROBLEMS_ERROR);
        }

        // 문항 리스트 검증
        List<ProblemAdminId> problemAdminIdList = request.problems().stream()
                .map(ProblemAdminId::new)
                .collect(Collectors.toList());
        problemAdminIdList.forEach(problemRepository::findByIdElseThrow);

        problemSet.updateProblemSet(request.problemSetTitle(), problemAdminIdList);
    }

    @Transactional
    public ProblemSetConfirmStatus toggleConfirmProblemSet(Long problemSetId) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);
        List<Problem> problems = new ArrayList<>();
        for (ProblemAdminId problemAdminId : problemSet.getProblemAdminIds()) {
            Problem problem = problemRepository.findByIdElseThrow(problemAdminId);
            problems.add(problem);
        }
        problemSet.toggleConfirm(problems);
        return problemSet.getConfirmStatus();
    }
}
