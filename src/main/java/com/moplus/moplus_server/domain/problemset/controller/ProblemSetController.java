package com.moplus.moplus_server.domain.problemset.controller;

import com.moplus.moplus_server.domain.problemset.dto.request.ProblemSetPostRequest;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetSaveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/problemSet")
@RequiredArgsConstructor
public class ProblemSetController {

    private final ProblemSetSaveService problemSetSaveService;

    @PostMapping("")
    @Operation(summary = "문항세트 생성", description = "세트를 생성합니다. 문항은 요청 순서대로 저장합니다.")
    public ResponseEntity<Long> createProblemSet(
            @RequestBody ProblemSetPostRequest request
    ) {
        return ResponseEntity.ok(problemSetSaveService.createProblemSet(request));
    }
}
