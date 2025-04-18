package com.moplus.moplus_server.client.problem.controller;

import com.moplus.moplus_server.client.problem.dto.response.AllProblemGetResponse;
import com.moplus.moplus_server.client.problem.dto.response.ChildProblemClientGetResponse;
import com.moplus.moplus_server.client.problem.dto.response.ChildProblemsClientGetResponse;
import com.moplus.moplus_server.client.problem.dto.response.ProblemClientGetResponse;
import com.moplus.moplus_server.client.problem.dto.response.ProblemClientThumbnailResponse;
import com.moplus.moplus_server.client.problem.dto.response.PublishClientGetResponse;
import com.moplus.moplus_server.client.problem.service.ClientProblemsGetService;
import com.moplus.moplus_server.global.annotation.AuthUser;
import com.moplus.moplus_server.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클라이언트 문제 조회", description = "클라이언트 문제 조회 관련 API")
@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ProblemGetController {

    private final ClientProblemsGetService clientProblemsGetService;

    @GetMapping("problem/all/{year}/{month}")
    @Operation(summary = "전체 문제 조회", description = "월별 문제들에 대한 진행도와 정보들을 조회합니다.")
    public ResponseEntity<List<AllProblemGetResponse>> getAllProblem(
            @PathVariable("year") int year,
            @PathVariable("month") int month,
            @AuthUser Member member
    ) {
        return ResponseEntity.ok(clientProblemsGetService.getAllProblem(member.getId(), year, month));
    }

    @GetMapping("problem/{publishId}")
    @Operation(summary = "특정 발행 속 문항들 조회", description = "사용자에게 보여지는 특정 발행에 속한 문항을 조회합니다.")
    public ResponseEntity<PublishClientGetResponse> getProblemsInPublish(
            @PathVariable("publishId") Long publishId,
            @AuthUser Member member
    ) {
        return ResponseEntity.ok(clientProblemsGetService.getProblemsInPublish(member.getId(), publishId));
    }

    @GetMapping("problem/{publishId}/{problemId}")
    @Operation(summary = "문항 조회", description = "사용자에게 보여지는 문항을 조회합니다.")
    public ResponseEntity<ProblemClientGetResponse> getProblem(
            @PathVariable("publishId") Long publishId,
            @PathVariable("problemId") Long problemId,
            @AuthUser Member member
    ) {
        return ResponseEntity.ok(clientProblemsGetService.getProblem(member.getId(), publishId, problemId));
    }

    @GetMapping("problem/{publishId}/{problemId}/{childProblemId}")
    @Operation(summary = "새끼문항 조회", description = "사용자에게 보여지는 새끼문항을 조회합니다.")
    public ResponseEntity<ChildProblemClientGetResponse> getChildProblem(
            @PathVariable("publishId") Long publishId,
            @PathVariable("problemId") Long problemId,
            @PathVariable("childProblemId") Long childProblemId,
            @AuthUser Member member
    ) {
        return ResponseEntity.ok(
                clientProblemsGetService.getChildProblem(member.getId(), publishId, problemId, childProblemId));
    }

    @GetMapping("problem/thumbnail/{publishId}/{problemId}")
    @Operation(summary = "문항 썸네일 조회", description = "바로 풀어보기/단계별로 풀어보기 화면에서 필요한 문항을 조회합니다.")
    public ResponseEntity<ProblemClientThumbnailResponse> getProblemThumbnail(
            @PathVariable Long publishId,
            @PathVariable Long problemId
    ) {
        return ResponseEntity.ok(clientProblemsGetService.getProblemThumbnail(publishId, problemId));
    }

    @GetMapping("problem/child/{publishId}/{problemId}")
    @Operation(summary = "문항에 포함된 새끼문항 정보 조회", description = "단계별로 풀어보기 이후 화면들에서 필요한 정보들을 조회합니다.")
    public ResponseEntity<ChildProblemsClientGetResponse> getChildProblems(
            @PathVariable Long publishId,
            @PathVariable Long problemId
    ) {
        return ResponseEntity.ok(clientProblemsGetService.getChildProblems(publishId, problemId));
    }
}
