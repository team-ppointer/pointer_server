package com.moplus.moplus_server.domain.practiceTest.controller;

import com.moplus.moplus_server.domain.practiceTest.dto.response.PracticeTestGetResponse;
import com.moplus.moplus_server.domain.practiceTest.service.PracticeTestService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/practiceTests")
public class PracticeTestController {

    private final PracticeTestService practiceTestService;

    @GetMapping("/all")
    @Operation(summary = "모든 모의고사 목록 조회 (검색용)")
    public ResponseEntity<List<PracticeTestGetResponse>> getAllPracticeTests() {
        return ResponseEntity.ok(practiceTestService.getAllPracticeTest());
    }

    @PutMapping("/{practiceTestid}/viewCount")
    @Operation(summary = "조회수 업데이트하기")
    public ResponseEntity<Void> updateViewCount(@PathVariable("practiceTestid") Long id) {
        practiceTestService.updateViewCount(id);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/{practiceTestid}/solveCount")
    @Operation(summary = "풀이수 업데이트하기")
    public ResponseEntity<Void> updateSolveCount(@PathVariable("practiceTestid") Long id) {
        practiceTestService.updateSolveCount(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{practiceTestid}")
    @Operation(summary = "모의고사 단건 조회하기")
    public ResponseEntity<PracticeTestGetResponse> getPracticeTest(@PathVariable("practiceTestid") Long id) {
        return ResponseEntity.ok(practiceTestService.getPracticeTestGetResponseForClient(id));
    }
}
