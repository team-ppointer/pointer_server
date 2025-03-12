package com.moplus.moplus_server.domain.problemset.service;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetGetRepositoryCustom;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.global.error.exception.BusinessException;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemSetGetService {

    private final ProblemSetRepository problemSetRepository;
    private final ProblemSetGetRepositoryCustom problemSetGetRepositoryCustom;

    @Transactional(readOnly = true)
    public ProblemSetGetResponse getProblemSet(Long problemSetId) {
        ProblemSet problemSet = problemSetRepository.findByIdElseThrow(problemSetId);
        if (problemSet.isDeleted()) {
            throw new BusinessException(ErrorCode.DELETE_PROBLEM_SET_GET_ERROR);
        }
        return problemSetGetRepositoryCustom.getProblemSet(problemSet);
    }
}
