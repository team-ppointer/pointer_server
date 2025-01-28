package com.moplus.moplus_server.domain.v0.practiceTest.api.admin;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.v0.practiceTest.service.client.PracticeTestService;
import com.moplus.moplus_server.domain.v0.practiceTest.service.client.ProblemService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Hidden
@Controller
@RequestMapping("/admin/practiceTests")
@RequiredArgsConstructor
public class ProblemAdminController {

    private final ProblemService problemService;
    private final PracticeTestService practiceTestService;

    @PostMapping("/submitAnswers")
    @Operation(summary = "모의고사 문제 답안 생성 요청")
    public String submitAnswers(@RequestParam("practiceTestId") Long practiceTestId, HttpServletRequest request) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(practiceTestId);

        problemService.saveProblems(practiceTest, request);

        return String.format("redirect:/admin/practiceTests/imageUploadPage/%d", practiceTestId);
    }

    @PostMapping("/submitAnswers/{id}")
    @Operation(summary = "모의고사 문제 답안 수정 요청")
    public String updateAnswers(@PathVariable("id") Long practiceTestId, HttpServletRequest request) {
        PracticeTest practiceTest = practiceTestService.getPracticeTestById(practiceTestId);
        problemService.updateProblems(practiceTest, request);

        return String.format("redirect:/admin/practiceTests/imageUploadPage/%d", practiceTestId);
    }
}
