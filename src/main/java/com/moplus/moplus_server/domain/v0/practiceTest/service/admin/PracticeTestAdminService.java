package com.moplus.moplus_server.domain.v0.practiceTest.service.admin;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.RatingTable;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.Subject;
import com.moplus.moplus_server.domain.v0.practiceTest.dto.admin.request.PracticeTestRequest;
import com.moplus.moplus_server.domain.v0.practiceTest.dto.admin.request.RatingTableRequest;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.ProblemRepository;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.RatingTableRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class PracticeTestAdminService {

    private final PracticeTestRepository practiceTestRepository;
    private final RatingTableRepository ratingTableRepository;
    private final ProblemRepository problemRepository;
    private final RatingTableAdminService ratingTableAdminService;

    private static void addToPracticeTestUpdateModel(Model model, List<RatingTable> ratingTables,
                                                     PracticeTest practiceTest) {
        if (!ratingTables.isEmpty()) {
            List<RatingTableRequest> ratingTableRequests = ratingTables.stream()
                    .map(RatingTableRequest::getRatingTableRequest)
                    .toList();
            model.addAttribute("practiceTestRequest",
                    PracticeTestRequest.getUpdateModelObject(practiceTest, ratingTableRequests));
        } else {
            model.addAttribute("practiceTestRequest", PracticeTestRequest.getUpdateModelObject(practiceTest));
        }
    }

    private static boolean isMathPracticeTest(PracticeTest practiceTest) {
        return List.of("미적분", "확률과통계", "기하", "고1", "고2").contains(practiceTest.getSubject().getValue());
    }

    public PracticeTest getPracticeTestById(Long id) {
        return practiceTestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));
    }

    public void getPracticeTestCreateModel(Model model) {
        model.addAttribute("practiceTestRequest", PracticeTestRequest.getCreateModelObject());
        model.addAttribute("subjects", Subject.values());
    }

    public void getPracticeTestUpdateModel(Model model, Long id) {
        PracticeTest practiceTest = getPracticeTestById(id);
        List<RatingTable> ratingTables = ratingTableAdminService.findRatingTableByPracticeId(id);
        addToPracticeTestUpdateModel(model, ratingTables, practiceTest);

        model.addAttribute("subjects", Subject.values());
    }

    @Transactional
    public void deletePracticeTest(Long id) {
        ratingTableRepository.deleteAllByPracticeTestId(id);
        problemRepository.deleteAllByPracticeTestId(id);
        practiceTestRepository.deleteById(id);
    }

    @Transactional
    public Long createPracticeTest(PracticeTestRequest request) {
        Long id = practiceTestRepository.save(request.toEntity()).getId();
        request.getRatingTableRequests()
                .forEach(ratingTableRequest -> ratingTableAdminService.createRatingTable(ratingTableRequest, id));
        return id;
    }

    public void getProblemCreateModel(Model model, Long practiceTestId) {
        PracticeTest practiceTest = getPracticeTestById(practiceTestId);
        int totalQuestions = practiceTest.getSubject().getProblemCount();

        model.addAttribute("practiceTestId", practiceTestId);
        model.addAttribute("practiceTest", practiceTest);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("hasShortAnswer", isMathPracticeTest(practiceTest));
        model.addAttribute("subject", practiceTest.getSubject().getValue());
    }

    @Transactional
    public void updatePracticeTest(Long practiceTestId, PracticeTestRequest request) {
        PracticeTest practiceTest = getPracticeTestById(practiceTestId);
        practiceTest.updateByPracticeTestRequest(request);

        ratingTableAdminService.updateOrSaveRatingTable(practiceTestId, request.getRatingTableRequests());

        practiceTestRepository.save(practiceTest);
    }

    public void getProblemUpdateModel(Model model, Long practiceTestId) {
        PracticeTest practiceTest = getPracticeTestById(practiceTestId);
        int totalQuestions = practiceTest.getSubject().getProblemCount();

        model.addAttribute("practiceTestId", practiceTestId);
        model.addAttribute("practiceTest", practiceTest);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("hasShortAnswer", isMathPracticeTest(practiceTest));
        model.addAttribute("subject", practiceTest.getSubject().getValue());
    }
}
