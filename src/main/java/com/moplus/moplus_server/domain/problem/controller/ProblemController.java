package com.moplus.moplus_server.domain.problem.controller;

import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.problem.service.ProblemDeleteService;
import com.moplus.moplus_server.domain.problem.service.ProblemGetService;
import com.moplus.moplus_server.domain.problem.service.ProblemSaveService;
import com.moplus.moplus_server.domain.problem.service.ProblemUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemSaveService problemSaveService;
    private final ProblemUpdateService problemUpdateService;
    private final ProblemGetService problemGetService;
    private final ProblemDeleteService problemDeleteService;

    @GetMapping("/{id}")
    @Operation(summary = "문항 조회", description = "문항를 조회합니다.")
    public ResponseEntity<ProblemGetResponse> getProblem(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(problemGetService.getProblem(id));
    }

    @PostMapping("")
    @Operation(summary = "문항 생성", description = "문제를 생성합니다. 새끼 문항은 list 순서대로 sequence를 저장합니다.")
    public ResponseEntity<Long> createProblem(
            @Valid @RequestBody ProblemPostRequest request
    ) {
        return ResponseEntity.ok(problemSaveService.createProblem(request));
    }

    @PostMapping("/{id}")
    @Operation(summary = "문항 업데이트", description = "문제를 업데이트합니다. 문항 번호, 모의고사는 수정할 수 없습니다. 새로 추가되는 새끼문항 id는 빈 값입니다.")
    public ResponseEntity<ProblemGetResponse> updateProblem(
            @PathVariable("id") Long id,
            @RequestBody ProblemUpdateRequest request
    ) {
        return ResponseEntity.ok(problemUpdateService.updateProblem(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "문항 삭제")
    public ResponseEntity<Void> updateProblem(
            @PathVariable("id") Long id
    ) {
        problemDeleteService.deleteProblem(id);
        return ResponseEntity.ok().body(null);
    }
}
