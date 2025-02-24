package com.moplus.moplus_server.domain.problemset.controller;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemReorderRequest;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemSetUpdateRequest;
import com.moplus.moplus_server.domain.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetDeleteService;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetGetService;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetSaveService;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetUpdateService;
import com.moplus.moplus_server.global.response.IdResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "문항세트", description = "문항세트 관련 API")
@RestController
@RequestMapping("/api/v1/problemSet")
@RequiredArgsConstructor
public class ProblemSetController {

    private final ProblemSetSaveService problemSetSaveService;
    private final ProblemSetUpdateService problemSetUpdateService;
    private final ProblemSetDeleteService problemSetDeleteService;
    private final ProblemSetGetService problemSetGetService;

    @PostMapping("")
    @Operation(summary = "문항세트 생성", description = "문항세트를 생성합니다. 문항은 요청 순서대로 저장합니다.")
    public ResponseEntity<IdResponse> createProblemSet(
    ) {
        return ResponseEntity.ok(new IdResponse(problemSetSaveService.createProblemSet()));
    }

    @Hidden
    @PutMapping("/{problemSetId}/sequence")
    @Operation(summary = "세트 문항순서 변경", description = "문항세트 내의 문항 리스트의 순서를 변경합니다.")
    public ResponseEntity<Void> reorderProblems(
            @PathVariable Long problemSetId,
            @RequestBody ProblemReorderRequest request) {
        problemSetUpdateService.reorderProblems(problemSetId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{problemSetId}")
    @Operation(summary = "문항세트 수정", description = "문항세트의 이름 및 문항 리스트를 수정합니다.")
    public ResponseEntity<Void> updateProblemSet(
            @PathVariable Long problemSetId,
            @RequestBody ProblemSetUpdateRequest request
    ) {
        problemSetUpdateService.updateProblemSet(problemSetId, request);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{problemSetId}")
    @Operation(summary = "문항세트 삭제", description = "문항세트를 삭제합니다. (soft delete)")
    public ResponseEntity<Void> deleteProblemSet(
            @PathVariable Long problemSetId
    ) {
        problemSetDeleteService.deleteProblemSet(problemSetId);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{problemSetId}/confirm")
    @Operation(summary = "문항세트 컨펌 토글", description = "문항세트의 컨펌 상태를 토글합니다.")
    public ResponseEntity<ProblemSetConfirmStatus> toggleConfirmProblemSet(
            @PathVariable Long problemSetId
    ) {
        return ResponseEntity.ok(problemSetUpdateService.toggleConfirmProblemSet(problemSetId));
    }

    @GetMapping("/{problemSetId}")
    @Operation(summary = "문항세트 개별 조회", description = "문항세트를 조회합니다.")
    public ResponseEntity<ProblemSetGetResponse> getProblemSet(
            @PathVariable("problemSetId") Long problemSetId
    ) {
        return ResponseEntity.ok(problemSetGetService.getProblemSet(problemSetId));
    }
}