package com.moplus.moplus_server.domain.problem.controller;

import com.moplus.moplus_server.domain.problem.dto.response.PracticeTestTagResponse;
import com.moplus.moplus_server.domain.problem.repository.PracticeTestTagRepository;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/practiceTestTags")
@RequiredArgsConstructor
public class PracticeTestTagController {

    private final PracticeTestTagRepository practiceTestTagRepository;

    @GetMapping("")
    @Operation(summary = "모의고사 목록 조회")
    public ResponseEntity<List<PracticeTestTagResponse>> getPracticeTestTags() {
        List<PracticeTestTagResponse> responses = practiceTestTagRepository.findAll().stream()
                .map(PracticeTestTagResponse::of)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
