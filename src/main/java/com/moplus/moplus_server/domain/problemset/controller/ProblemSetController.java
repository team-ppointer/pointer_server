package com.moplus.moplus_server.domain.problemset.controller;

import com.moplus.moplus_server.domain.problemset.dto.request.ProblemReorderRequest;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemSetPostRequest;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemSetUpdateRequest;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetSaveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/problemSet")
@RequiredArgsConstructor
public class ProblemSetController {

    private final ProblemSetSaveService problemSetSaveService;

    @PostMapping("")
    @Operation(summary = "문항세트 생성", description = "문항세트를 생성합니다. 문항은 요청 순서대로 저장합니다.")
    public ResponseEntity<Long> createProblemSet(
            @RequestBody ProblemSetPostRequest request
    ) {
        return ResponseEntity.ok(problemSetSaveService.createProblemSet(request));
    }

    @PutMapping("/{problemSetId}/sequence")
    @Operation(summary = "세트 문항순서 변경", description = "문항세트 내의 문항 리스트의 순서를 변경합니다.")
    public ResponseEntity<Void> reorderProblems(
            @PathVariable Long problemSetId,
            @RequestBody ProblemReorderRequest request) {
        problemSetSaveService.reorderProblems(problemSetId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{problemSetId}")
    @Operation(summary = "문항세트 수정", description = "문항세트의 이름 및 문항 리스트를 수정합니다.")
    public ResponseEntity<Void> updateProblemSet(
            @PathVariable Long problemSetId,
            @RequestBody ProblemSetUpdateRequest request
    ) {
        problemSetSaveService.updateProblemSet(problemSetId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{problemSetId}")
    @Operation(summary = "문항세트 삭제", description = "문항세트를 삭제합니다. (soft delete)")
    public ResponseEntity<Void> deleteProblemSet(
            @PathVariable Long problemSetId
    ) {
        problemSetSaveService.deleteProblemSet(problemSetId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{problemSetId}/confirm")
    @Operation(summary = "문항세트 컨펌 토글", description = "문항세트의 컨펌 상태를 토글합니다.")
    public ResponseEntity<Boolean> toggleConfirmProblemSet(@PathVariable Long problemSetId) {
        boolean updatedState = problemSetSaveService.toggleConfirmProblemSet(problemSetId);
        return ResponseEntity.ok(updatedState);
    }
}
