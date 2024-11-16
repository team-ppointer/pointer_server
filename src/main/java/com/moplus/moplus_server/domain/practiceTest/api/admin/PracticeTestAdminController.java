package com.moplus.moplus_server.domain.practiceTest.api.admin;

import com.moplus.moplus_server.domain.practiceTest.dto.client.response.PracticeTestGetResponse;
import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.service.client.PracticeTestService;
import com.moplus.moplus_server.domain.practiceTest.service.client.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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



}
