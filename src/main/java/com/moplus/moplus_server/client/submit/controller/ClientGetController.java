package com.moplus.moplus_server.client.submit.controller;

import com.moplus.moplus_server.client.submit.dto.response.AllProblemGetResponse;
import com.moplus.moplus_server.client.submit.dto.response.ChildProblemClientGetResponse;
import com.moplus.moplus_server.client.submit.dto.response.CommentaryGetResponse;
import com.moplus.moplus_server.client.submit.dto.response.ProblemClientGetResponse;
import com.moplus.moplus_server.client.submit.service.ClientGetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클라이언트 조회", description = "클라이언트 조회 관련 API")
@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientGetController {

    private final ClientGetService clientGetService;

    @GetMapping("commentary")
    @Operation(summary = "해설 조회", description = "문항 별 해설/처방을 조회합니다.")
    public ResponseEntity<CommentaryGetResponse> getCommentary(
            @RequestParam(value = "publishId", required = false) Long publishId,
            @RequestParam(value = "problemId", required = false) Long problemId
    ) {
        return ResponseEntity.ok(clientGetService.getCommentary(publishId, problemId));
    }

    @GetMapping("allProblem/{year}/{month}")
    @Operation(summary = "전체 문제 조회", description = "월별 문제들에 대한 진행도와 정보들을 조회합니다.")
    public ResponseEntity<List<AllProblemGetResponse>> getAllProblem(
            @PathVariable int year,
            @PathVariable int month
    ) {
        return ResponseEntity.ok(clientGetService.getAllProblem(year, month));
    }

    @GetMapping("problem/{publishId}/{problemId}")
    @Operation(summary = "문항 조회", description = "사용자에게 보여지는 문항을 조회합니다.")
    public ResponseEntity<ProblemClientGetResponse> getProblem(
            @PathVariable Long publishId,
            @PathVariable Long problemId
    ) {
        return ResponseEntity.ok(clientGetService.getProblem(publishId, problemId));
    }

    @GetMapping("problem/{publishId}/{problemId}/{childProblemId}")
    @Operation(summary = "새끼문항 조회", description = "사용자에게 보여지는 새끼문항을 조회합니다.")
    public ResponseEntity<ChildProblemClientGetResponse> getChildProblem(
            @PathVariable Long publishId,
            @PathVariable Long problemId,
            @PathVariable Long childProblemId
    ) {
        return ResponseEntity.ok(clientGetService.getChildProblem(publishId, problemId, childProblemId));
    }
}
