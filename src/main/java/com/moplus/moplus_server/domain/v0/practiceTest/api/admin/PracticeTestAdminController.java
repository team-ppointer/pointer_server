package com.moplus.moplus_server.domain.v0.practiceTest.api.admin;

import com.moplus.moplus_server.domain.v0.practiceTest.dto.client.response.PracticeTestGetResponse;
import com.moplus.moplus_server.domain.v0.practiceTest.service.client.PracticeTestService;
import com.moplus.moplus_server.domain.v0.practiceTest.service.client.ProblemService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Hidden
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
