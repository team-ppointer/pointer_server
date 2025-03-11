package com.moplus.moplus_server.admin.problem.controller;

import com.moplus.moplus_server.admin.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.admin.problem.dto.request.ProblemUpdateRequest;
import com.moplus.moplus_server.admin.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.admin.problem.dto.response.ProblemPostResponse;
import com.moplus.moplus_server.domain.problem.service.ChildProblemService;
import com.moplus.moplus_server.domain.problem.service.ProblemDeleteService;
import com.moplus.moplus_server.domain.problem.service.ProblemGetService;
import com.moplus.moplus_server.domain.problem.service.ProblemSaveService;
import com.moplus.moplus_server.domain.problem.service.ProblemUpdateService;
import com.moplus.moplus_server.global.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@Tag(name = "문항", description = "문항 관련 API")
@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemSaveService problemSaveService;
    private final ProblemUpdateService problemUpdateService;
    private final ProblemGetService problemGetService;
    private final ProblemDeleteService problemDeleteService;
    private final ChildProblemService childProblemService;

    @GetMapping("/{id}")
    @Operation(summary = "문항 조회", description = "문항를 조회합니다.")
    public ResponseEntity<ProblemGetResponse> getProblem(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(problemGetService.getProblem(id));
    }

    @PostMapping("")
    @Operation(summary = "문항 생성", description = "문제를 생성합니다. 기출/변형 문제는 모든 값이 필수이며 창작 문제는 문항 타입만 필수 입니다.")
    public ResponseEntity<ProblemPostResponse> createProblem(
            @Valid @RequestBody ProblemPostRequest request
    ) {
        return ResponseEntity.ok(problemSaveService.createProblem(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "문항 업데이트", description = "문제를 업데이트합니다. 새끼문항은 들어온 list의 순서로 저장됩니다.")
    public ResponseEntity<ProblemGetResponse> updateProblem(
            @PathVariable("id") Long id,
            @RequestBody ProblemUpdateRequest request
    ) {
        return ResponseEntity.ok(problemUpdateService.updateProblem(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "문항 삭제")
    public ResponseEntity<Void> deleteProblem(
            @PathVariable("id") Long id
    ) {
        problemDeleteService.deleteProblem(id);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/{problemId}/child-problems")
    @Operation(summary = "새끼문항 추가", description = "추가되는 새끼 문항의 id를 반환합니다. 컨펌 이후에는 새끼 문항 추가가 불가능합니다.")
    public ResponseEntity<IdResponse> createChildProblem(
            @PathVariable("problemId") Long problemId
    ) {
        return ResponseEntity.ok(new IdResponse(childProblemService.createChildProblem(problemId)));
    }

    @DeleteMapping("/{problemId}/child-problems/{childProblemId}")
    @Operation(summary = "새끼 문항 삭제", description = "컨펌 이후에는 새끼 문항 삭제가 불가능합니다.")
    public ResponseEntity<Void> deleteChildProblem(
            @PathVariable("problemId") Long problemId,
            @PathVariable("childProblemId") Long childProblemId
    ) {
        childProblemService.deleteChildProblem(problemId, childProblemId);
        return ResponseEntity.ok().body(null);
    }
}
