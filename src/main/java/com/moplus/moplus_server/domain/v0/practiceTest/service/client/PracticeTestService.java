package com.moplus.moplus_server.domain.v0.practiceTest.service.client;

import com.moplus.moplus_server.domain.v0.practiceTest.dto.client.response.PracticeTestGetResponse;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PracticeTestService {

    private final PracticeTestRepository practiceTestRepository;

    @Transactional(readOnly = true)
    @Cacheable("allPracticeTests")
    public List<PracticeTestGetResponse> getAllPracticeTest() {
        return practiceTestRepository.findAllByOrderByViewCountDesc().stream()
                .map(PracticeTestGetResponse::from)
                .toList();
    }

    @Transactional
    public void updateViewCount(Long id) {
        PracticeTest practiceTest = practiceTestRepository.findByIdWithPessimisticLock(id);
        practiceTest.plus1ViewCount();
        practiceTestRepository.saveAndFlush(practiceTest);
    }

    @Transactional
    public void updateSolveCount(Long id) {
        PracticeTest practiceTest = getPracticeTestById(id);
        practiceTest.plus1SolvesCount();
    }

    @Transactional(readOnly = true)
    public PracticeTest getPracticeTestById(Long id) {
        return practiceTestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public PracticeTestGetResponse getPracticeTestGetResponseForClient(Long id) {
        return PracticeTestGetResponse.from(getPracticeTestById(id));
    }


}
