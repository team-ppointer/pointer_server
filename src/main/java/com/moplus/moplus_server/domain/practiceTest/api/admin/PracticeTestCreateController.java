package com.moplus.moplus_server.domain.practiceTest.api.admin;

import com.moplus.moplus_server.domain.practiceTest.dto.admin.request.PracticeTestRequest;
import com.moplus.moplus_server.domain.practiceTest.dto.client.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.service.admin.PracticeTestAdminService;
import com.moplus.moplus_server.domain.practiceTest.service.client.PracticeTestService;
import com.moplus.moplus_server.domain.practiceTest.service.client.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/practiceTests")
@RequiredArgsConstructor
public class PracticeTestCreateController {

    private final PracticeTestService practiceTestService;
    private final PracticeTestAdminService practiceTestAdminService;
    private final ProblemService problemService;

    @GetMapping("/testInputForm")
    @Operation(summary = "모의고사 정보 생성 페이지 HTML 요청")
    public String showForm(Model model) {
        practiceTestAdminService.getPracticeTestCreateModel(model);
        return "testInputForm";
    }

    @GetMapping("/testInputForm/{id}")
    @Operation(summary = "모의고사 정보 수정 페이지 HTML 요청")
    public String showFormById(Model model, @PathVariable("id") Long id) {
        practiceTestAdminService.getPracticeTestUpdateModel(model, id);
        return "testInputForm";
    }

    @PostMapping("/submit")
    @Operation(summary = "모의고사 정보 저장 요청")
    public String updateForm(@ModelAttribute PracticeTestRequest practiceTestRequest, Model model) {
        Long practiceTestId = practiceTestService.createPracticeTest(practiceTestRequest);

        PracticeTest practiceTest = practiceTestService.getPracticeTestById(practiceTestId);
        int totalQuestions = practiceTest.getSubject().getProblemCount();
        boolean hasShortAnswer = false;

        hasShortAnswer = List.of("미적분", "확률과통계", "기하").contains(practiceTest.getSubject().getValue());

        model.addAttribute("practiceTestId", practiceTestId);
        model.addAttribute("practiceTest", practiceTest);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("hasShortAnswer", hasShortAnswer);
        // 리다이렉트 처리
        return "answerInputForm";
    }

    @PostMapping("/submit/{id}")
    @Operation(summary = "모의고사 정보 수정 요청")
    public String updateForm(@PathVariable("id") Long id, @ModelAttribute PracticeTestRequest practiceTestRequest, Model model) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(id);

        practiceTestService.updatePracticeTest(id, practiceTestRequest);

        int totalQuestions = practiceTest.getSubject().getProblemCount();
        boolean hasShortAnswer = false;
        List<ProblemGetResponse> problems = problemService.getProblemsByTestId(id);

        hasShortAnswer = List.of("미적분", "확률과통계", "기하").contains(practiceTest.getSubject().getValue());

        model.addAttribute("problems", problems);
        model.addAttribute("practiceTestId", id);
        model.addAttribute("practiceTest", practiceTest);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("hasShortAnswer", hasShortAnswer);

        return "answerInputForm";
    }

    @PostMapping("/{id}/delete")
    @Operation(summary = "모의고사 정보 삭제 요청")
    public String deletePracticeTest(@PathVariable("id") Long id) {
        practiceTestAdminService.deletePracticeTest(id);
        return "redirect:/practiceTests";
    }
}
