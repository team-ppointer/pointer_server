package com.moplus.moplus_server.domain.problemset.service;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemReorderRequest;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemSetUpdateRequest;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.domain.publish.domain.Publish;
import com.moplus.moplus_server.domain.publish.repository.PublishRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemSetUpdateService {

    private final ProblemSetRepository problemSetRepository;
    private final ProblemRepository problemRepository;
    private final PublishRepository publishRepository;

    @Transactional
    public void reorderProblems(Long problemSetId, ProblemReorderRequest request) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);

        problemSet.updateProblemOrder(request.newProblems());
    }

    @Transactional
    public void updateProblemSet(Long problemSetId, ProblemSetUpdateRequest request) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);

        // 빈 문항 유효성 검증
        if (request.problemIds().isEmpty()) {
            throw new InvalidValueException(ErrorCode.EMPTY_PROBLEMS_ERROR);
        }

        request.problemIds().forEach(problemRepository::findByIdElseThrow);

        problemSet.updateProblemSet(request.problemSetTitle(), request.problemIds());
    }

    @Transactional
    public ProblemSetConfirmStatus toggleConfirmProblemSet(Long problemSetId) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);
        List<Publish> publishes = publishRepository.findByProblemSetId(problemSetId);
        if (!publishes.isEmpty()) {
            throw new InvalidValueException(ErrorCode.ALREADY_PUBLISHED_ERROR);
        }

        List<Problem> problems = new ArrayList<>();
        for (Long problemId : problemSet.getProblemIds()) {
            Problem problem = problemRepository.findByIdElseThrow(problemId);
            problems.add(problem);
        }
        problemSet.toggleConfirm(problems);
        return problemSet.getConfirmStatus();
    }
}
