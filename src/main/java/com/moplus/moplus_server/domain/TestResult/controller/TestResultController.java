package com.moplus.moplus_server.domain.TestResult.controller;

import com.moplus.moplus_server.domain.TestResult.dto.request.IncorrectProblemPostRequest;
import com.moplus.moplus_server.domain.TestResult.dto.request.SolvingTimePostRequest;
import com.moplus.moplus_server.domain.TestResult.dto.response.TestResultGetResponse;
import com.moplus.moplus_server.domain.TestResult.service.TestResultService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/testResult")
public class TestResultController {

    private final TestResultService testResultService;

    @PostMapping("/{practiceTestId}/uploadingAnswer")
    @Operation(summary = "답 입력 결과 제출", description = "테스트 결과지의 ID를 반환합니다.")
    public ResponseEntity<Long> uploadTestAnswers(@PathVariable("practiceTestId") Long id,
        @RequestBody List<IncorrectProblemPostRequest> requests) {
        return ResponseEntity.ok(testResultService.createTestResult(id, requests));
    }

    @GetMapping("/{testResultId}")
    @Operation(summary = "공유용 테스트 결과 가져오기")
    public ResponseEntity<TestResultGetResponse> getTestAnswers(@PathVariable("testResultId") Long id) {
        return ResponseEntity.ok(testResultService.getTestResult(id));
    }

    @PostMapping("/{testResultId}/uploadingMinute")
    @Operation(summary = "풀이 시간 제출 및 시험 결과지 받기",
        description = "성적과 풀이시간에 기반한 내 위치 결과지를 반환합니다. 풀이시간은 'PT{시간}H{분}M' 형식으로 보내주세요")
    public ResponseEntity<TestResultGetResponse> uploadSolvingMinute(
        @PathVariable("testResultId") Long id,
        @RequestBody SolvingTimePostRequest request
        ) {
        return ResponseEntity.ok(testResultService.getTestResultBySolvingTime(id, request));
    }
}
