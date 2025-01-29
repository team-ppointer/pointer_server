package com.moplus.moplus_server.domain.problem.controller;

import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.problem.service.ProblemSaveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemSaveController {

    private final ProblemSaveService problemSaveService;

    @PostMapping("")
    @Operation(summary = "문항 생성", description = "문제를 생성합니다. 새끼 문항은 list 순서대로 sequence를 저장합니다.")
    public ResponseEntity<String> createProblem(
            @RequestBody ProblemPostRequest request
    ) {
        return ResponseEntity.ok(problemSaveService.createProblem(request).toString());
    }

    @PostMapping("/{id}")
    @Operation(summary = "문항 업데이트", description = "문제를 업데이트합니다. 새끼 문항은 수정된 리스트, 새로 생성된 리스트, 삭제된 리스트가 필요합니다.")
    public ResponseEntity<ProblemGetResponse> updateProblem(
            @PathVariable("id") String id,
            @RequestBody ProblemUpdateRequest request
    ) {
        return ResponseEntity.ok(problemSaveService.updateProblem(id, request));
    }
}
