package com.moplus.moplus_server.domain.practiceTest.controller;

import com.moplus.moplus_server.domain.practiceTest.dto.request.PracticeTestCreateRequest;
import com.moplus.moplus_server.domain.practiceTest.dto.response.PracticeTestGetResponse;
import com.moplus.moplus_server.domain.practiceTest.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.entity.Subject;
import com.moplus.moplus_server.domain.practiceTest.service.PracticeTestService;
import com.moplus.moplus_server.domain.practiceTest.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PracticeTestAdminController {

    private final PracticeTestService practiceTestService;
    private final ProblemService problemService;

    // 전체 목록 보기
    @GetMapping("/practiceTests")
    @Operation(summary = "모든 모의고사 목록 조회")
    public String listPracticeTests(Model model) {
        List<PracticeTestGetResponse> practiceTests = practiceTestService.getAllPracticeTest();
        model.addAttribute("practiceTests", practiceTests);
        return "practiceTestList";
    }

    @GetMapping("/testInputForm")
    @Operation(summary = "모의고사 정보 생성 페이지")
    public String showForm(Model model) {
        model.addAttribute("practiceTestCreateRequest", new PracticeTestCreateRequest(null,"", "", "", "",null, new Integer[]{},new Integer[]{},new Integer[]{}));
        model.addAttribute("subjects", Subject.values());
        return "testInputForm";
    }

    @GetMapping("/testInputForm/{id}")
    @Operation(summary = "모의고사 정보 수정 페이지")
    public String showFormById(Model model, @PathVariable("id") Long id) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(id);
        model.addAttribute("practiceTestCreateRequest", new PracticeTestCreateRequest(
            practiceTest.getId(),practiceTest.getName(), practiceTest.getRound(), practiceTest.getProvider(),
            practiceTest.getPublicationYear(), practiceTest.getSubject().getValue(), new Integer[]{},new Integer[]{},new Integer[]{}));
        model.addAttribute("subjects", Subject.values());
        return "testInputForm";
    }

    @PostMapping("/submit")
    @Operation(summary = "모의고사 정보 생성 요청")
    public String updateForm(@ModelAttribute PracticeTestCreateRequest practiceTestCreateRequest, RedirectAttributes redirectAttributes) {

        Long practiceTestId = practiceTestService.createPracticeTest(practiceTestCreateRequest);

        redirectAttributes.addAttribute("id", practiceTestId);

        // 리다이렉트 처리
        return "redirect:/practiceTest/upload/{id}";
    }

    @PostMapping("/submit/{id}")
    @Operation(summary = "모의고사 정보 수정 요청")
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
    @Operation(summary = "모의고사 정보 생성 요청")
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
    @Operation(summary = "모의고사 문제 답안 생성 요청")
    public String submitAnswers(@RequestParam("practiceTestId") Long practiceTestId, HttpServletRequest request) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(practiceTestId);

        problemService.saveProblems(practiceTest, request);

        return "redirect:/practiceTests";
    }

    @PostMapping("/submitAnswers/{id}")
    @Operation(summary = "모의고사 문제 답안 수정 요청")
    public String updateAnswers(@PathVariable("id") Long id, HttpServletRequest request) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(id);
        problemService.updateProblems(practiceTest, request);

        return "redirect:/practiceTests";
    }

}
