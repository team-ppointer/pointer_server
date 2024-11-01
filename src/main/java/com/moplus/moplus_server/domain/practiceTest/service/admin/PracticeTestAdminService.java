package com.moplus.moplus_server.domain.practiceTest.service.admin;

import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.domain.RatingTable;
import com.moplus.moplus_server.domain.practiceTest.domain.Subject;
import com.moplus.moplus_server.domain.practiceTest.dto.admin.request.PracticeTestRequest;
import com.moplus.moplus_server.domain.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.domain.practiceTest.repository.ProblemRepository;
import com.moplus.moplus_server.domain.practiceTest.repository.RatingTableRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class PracticeTestAdminService {

    private final PracticeTestRepository practiceTestRepository;
    private final RatingTableRepository ratingTableRepository;
    private final ProblemRepository problemRepository;

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
        Optional<RatingTable> optionalRatingTalble = ratingTableRepository.findByPracticeTestIdAndSubject(id,
                practiceTest.getSubject());
        if(optionalRatingTalble.isPresent()) {
            RatingTable ratingTable = optionalRatingTalble.get();
            model.addAttribute("practiceTestRequest", PracticeTestRequest.getUpdateModelObject(practiceTest, ratingTable.getRatingRows()));
        } else {
            model.addAttribute("practiceTestRequest", PracticeTestRequest.getUpdateModelObject(practiceTest));
        }
        model.addAttribute("subjects", Subject.values());
    }

    @Transactional
    public void deletePracticeTest(Long id) {
        problemRepository.deleteAllByPracticeTestId(id);
        practiceTestRepository.deleteById(id);
    }
}
