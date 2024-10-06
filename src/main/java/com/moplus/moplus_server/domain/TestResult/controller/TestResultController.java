package com.moplus.moplus_server.domain.TestResult.controller;

import com.moplus.moplus_server.domain.TestResult.dto.request.IncorrectProblemPostRequest;
import com.moplus.moplus_server.domain.TestResult.service.TestResultService;
import com.moplus.moplus_server.domain.practiceTest.dto.response.PracticeTestGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/testResult")
public class TestResultController {

    private final TestResultService testResultService;

    @PostMapping("/uploadingAnswer")
    @Operation(summary = "답 입력 결과 제출", description = "테스트 결과지의 ID를 반환합니다.")
    public ResponseEntity<Long> uploadTestAnswers(@PathVariable("practiceTestId") Long id,
        List<IncorrectProblemPostRequest> requests) {
        return ResponseEntity.ok(testResultService.createTestResult(id, requests));
    }


}
