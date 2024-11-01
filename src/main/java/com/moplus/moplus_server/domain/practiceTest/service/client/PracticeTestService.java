package com.moplus.moplus_server.domain.practiceTest.service.client;

import com.moplus.moplus_server.domain.practiceTest.dto.admin.request.PracticeTestRequest;
import com.moplus.moplus_server.domain.practiceTest.dto.admin.response.PracticeTestAdminResponse;
import com.moplus.moplus_server.domain.practiceTest.dto.client.response.PracticeTestGetResponse;
import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.domain.Subject;
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

    public List<PracticeTestGetResponse> getAllPracticeTest(){
        return practiceTestRepository.findAllByOrderByViewCountDesc().stream()
            .map(PracticeTestGetResponse::from)
            .toList();
    }

    @Transactional
    public void updateViewCount(Long id) {
        PracticeTest practiceTest = getPracticeTestById(id);
        practiceTest.plus1ViewCount();
    }

    @Transactional
    public void updateSolveCount(Long id) {
        PracticeTest practiceTest = getPracticeTestById(id);
        practiceTest.plus1SolvesCount();
    }

    @Transactional
    public void updatePracticeTest(Long practiceTestId, PracticeTestRequest request) {
        PracticeTest practiceTest = practiceTestRepository.findById(practiceTestId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));

        practiceTest.updateName(request.getName());
        practiceTest.updateProvider(request.getProvider());
        practiceTest.updateSubject(Subject.fromValue(request.getSubject()));
        practiceTest.updatePublicationYear(request.getPublicationYear());
        practiceTest.updateRound(request.getRound());

        practiceTestRepository.save(practiceTest);
    }

    public PracticeTestAdminResponse getPracticeTestResponseByIdForAdmin(Long id) {
        PracticeTest practiceTest = practiceTestRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));

        return PracticeTestAdminResponse.from(practiceTest);
    }

    public PracticeTest getPracticeTestById(Long id) {
        return practiceTestRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));
    }

    public PracticeTestGetResponse getPracticeTestGetResponseForClient(Long id) {
        return PracticeTestGetResponse.from(getPracticeTestById(id));
    }

    @Transactional
    public Long createPracticeTest(PracticeTestRequest request) {
        return practiceTestRepository.save(request.toEntity()).getId();
    }
}
