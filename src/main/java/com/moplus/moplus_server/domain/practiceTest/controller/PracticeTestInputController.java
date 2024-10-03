package com.moplus.moplus_server.domain.practiceTest.controller;

import com.moplus.moplus_server.domain.practiceTest.dto.request.PracticeTestCreateRequest;
import com.moplus.moplus_server.domain.practiceTest.dto.response.PracticeTestResponse;
import com.moplus.moplus_server.domain.practiceTest.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.entity.Subject;
import com.moplus.moplus_server.domain.practiceTest.service.PracticeTestService;
import com.moplus.moplus_server.domain.practiceTest.service.ProblemService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PracticeTestInputController {

    private final PracticeTestService practiceTestService;
    private final ProblemService problemService;

    // 전체 목록 보기
    @GetMapping("/practiceTests")
    public String listPracticeTests(Model model) {
        List<PracticeTestResponse> practiceTests = practiceTestService.getAllPracticeTest();
        model.addAttribute("practiceTests", practiceTests);
        return "practiceTestList";
    }

    @GetMapping("/testInputForm")
    public String showForm(Model model) {
        model.addAttribute("practiceTestCreateRequest", new PracticeTestCreateRequest(null,"", "", "", null));
        model.addAttribute("subjects", Subject.values());
        return "testInputForm";
    }

    @GetMapping("/testInputForm/{id}")
    public String showFormById(Model model, @PathVariable("id") Long id) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(id);
        model.addAttribute("practiceTestCreateRequest", new PracticeTestCreateRequest(practiceTest.getId(),practiceTest.getName(),
            practiceTest.getRound(), practiceTest.getProvider(), practiceTest.getSubject().getValue()));
        model.addAttribute("subjects", Subject.values());
        return "testInputForm";
    }

    @PostMapping("/submit")
    public String updateForm(@ModelAttribute PracticeTestCreateRequest practiceTestCreateRequest, RedirectAttributes redirectAttributes) {

        Long practiceTestId = practiceTestService.createPracticeTest(practiceTestCreateRequest);

        redirectAttributes.addAttribute("id", practiceTestId);

        // 리다이렉트 처리
        return "redirect:/practiceTest/upload/{id}";
    }

    @PostMapping("/submit/{id}")
    public String updateForm(@PathVariable("id") Long id,@ModelAttribute PracticeTestCreateRequest practiceTestCreateRequest, Model model) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(id);
        practiceTestService.updatePracticeTest(id, practiceTestCreateRequest);
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

    @GetMapping("/practiceTest/upload/{id}")
    public String showPracticeTestForm(@PathVariable("id") Long id, Model model) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(id);
        int totalQuestions = practiceTest.getSubject().getProblemCount();
        boolean hasShortAnswer = false;

        hasShortAnswer = List.of("미적분", "확률과통계", "기하").contains(practiceTest.getSubject().getValue());

        model.addAttribute("practiceTestId", id);
        model.addAttribute("practiceTest", practiceTest);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("hasShortAnswer", hasShortAnswer);

        return "answerInputForm";
    }

    @PostMapping("/submitAnswers")
    public String submitAnswers(@RequestParam("practiceTestId") Long practiceTestId, HttpServletRequest request) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(practiceTestId);

        problemService.saveProblems(practiceTest, request);

        return "redirect:/practiceTests";
    }

    @PostMapping("/submitAnswers/{id}")
    public String updateAnswers(@PathVariable("id") Long id, HttpServletRequest request) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(id);
        problemService.updateProblems(practiceTest, request);

        return "redirect:/practiceTests";
    }

}
