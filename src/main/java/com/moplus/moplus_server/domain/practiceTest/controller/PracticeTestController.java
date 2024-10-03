package com.moplus.moplus_server.domain.practiceTest.controller;

import com.moplus.moplus_server.domain.practiceTest.dto.response.PracticeTestGetResponse;
import com.moplus.moplus_server.domain.practiceTest.service.PracticeTestService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/practiceTests")
public class PracticeTestController {

    private final PracticeTestService practiceTestService;

    @GetMapping("/all")
    @Operation(summary = "진출분야로만 각 역량에 해당하는 강좌 필터링하기")
    public ResponseEntity<List<PracticeTestGetResponse>> getAllPracticeTests() {
        return ResponseEntity.ok(practiceTestService.getAllPracticeTest());
    }
}
