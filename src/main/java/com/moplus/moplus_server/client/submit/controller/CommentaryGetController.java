package com.moplus.moplus_server.client.submit.controller;

import com.moplus.moplus_server.client.submit.dto.response.CommentaryGetResponse;
import com.moplus.moplus_server.client.submit.service.CommentaryGetService;
import com.moplus.moplus_server.global.annotation.AuthUser;
import com.moplus.moplus_server.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "클라이언트 해설 조회", description = "클라이언트 해설 조회 관련 API")
@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class CommentaryGetController {

    private final CommentaryGetService commentaryGetService;

    @GetMapping("commentary")
    @Operation(summary = "해설 조회", description = "문항 별 해설/처방을 조회합니다.")
    public ResponseEntity<CommentaryGetResponse> getCommentary(
            @RequestParam(value = "publishId", required = false) Long publishId,
            @RequestParam(value = "problemId", required = false) Long problemId,
            @AuthUser Member member
            ) {
        return ResponseEntity.ok(commentaryGetService.getCommentary(member.getId(), publishId, problemId));
    }
}
