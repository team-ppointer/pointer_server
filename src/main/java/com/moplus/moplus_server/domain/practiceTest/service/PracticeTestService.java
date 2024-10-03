package com.moplus.moplus_server.domain.practiceTest.service;

import com.moplus.moplus_server.domain.practiceTest.dto.request.PracticeTestCreateRequest;
import com.moplus.moplus_server.domain.practiceTest.dto.response.PracticeTestResponse;
import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.entity.Subject;
import com.moplus.moplus_server.domain.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PracticeTestService {

    private final PracticeTestRepository practiceTestRepository;

    public List<PracticeTestResponse> getAllPracticeTest(){
        return practiceTestRepository.findAll().stream()
            .map(PracticeTestResponse::from)
            .toList();
    }

    public void updatePracticeTest(Long practiceTestId, PracticeTestCreateRequest request) {
        PracticeTest practiceTest = practiceTestRepository.findById(practiceTestId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));

        practiceTest.updateName(request.name());
        practiceTest.updateProvider(request.provider());
        practiceTest.updateSubject(Subject.fromValue(request.subject()));
        practiceTest.updateRound(request.round());

        practiceTestRepository.save(practiceTest);
    }

    public PracticeTestResponse getPracticeTestResponseById(Long id) {
        PracticeTest practiceTest = practiceTestRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));

        return PracticeTestResponse.from(practiceTest);
    }

    public PracticeTest getPracticeTestById(Long id) {

        return practiceTestRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));
    }

    @Transactional
    public Long createPracticeTest(PracticeTestCreateRequest request) {
        return practiceTestRepository.save(request.toEntity()).getId();
    }
}
