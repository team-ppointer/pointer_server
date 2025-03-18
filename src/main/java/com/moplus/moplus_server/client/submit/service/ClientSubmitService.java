package com.moplus.moplus_server.client.submit.service;


import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import com.moplus.moplus_server.client.submit.dto.request.ProblemSubmitCreateRequest;
import com.moplus.moplus_server.client.submit.repository.ProblemSubmitRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientSubmitService {

    private final ProblemSubmitRepository problemSubmitRepository;

    @Transactional
    public void createProblemSubmit(ProblemSubmitCreateRequest request) {

        Long memberId = 1L;
        // 제출이력이 없을때만 생성
        Optional<ProblemSubmit> existingProblemSubmit = problemSubmitRepository.findByMemberIdAndPublishIdAndProblemId(memberId,
                request.publishId(), request.problemId());
        if (existingProblemSubmit.isEmpty()) {
            ProblemSubmit problemSubmit = request.toEntity(memberId);
            problemSubmitRepository.save(problemSubmit);
        }
    }
}
