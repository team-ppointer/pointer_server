package com.moplus.moplus_server.domain.problem.controller;

import com.moplus.moplus_server.domain.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.problem.service.ProblemGetService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemGetController {

    private final ProblemGetService problemGetService;

    @GetMapping("/{id}")
    @Operation(summary = "문항 조회", description = "문항를 조회합니다.")
    public ResponseEntity<ProblemGetResponse> getProblem(
            @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(problemGetService.getProblem(id));
    }
}
