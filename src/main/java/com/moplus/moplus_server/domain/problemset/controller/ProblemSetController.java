package com.moplus.moplus_server.domain.problemset.controller;

import com.moplus.moplus_server.domain.problemset.dto.request.ProblemReorderRequest;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemSetPostRequest;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetSaveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            @RequestBody ProblemReorderRequest problemReorderRequest) {
        problemSetSaveService.reorderProblems(problemSetId, problemReorderRequest);
        return ResponseEntity.noContent().build();
    }
}
