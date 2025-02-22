package com.moplus.moplus_server.domain.problem.controller;

import com.moplus.moplus_server.domain.problem.dto.response.ProblemSearchGetResponse;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemSearchResponse;
import com.moplus.moplus_server.domain.problem.service.ProblemSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "문항", description = "문항 관련 API")
@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemSearchController {

    private final ProblemSearchService problemSearchService;

    @GetMapping("/search")
    @Operation(summary = "문제 검색 (페이징 없음)")
    public ResponseEntity<List<ProblemSearchGetResponse>> search(
            @RequestParam(value = "problemCustomId", required = false) String problemCustomId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "memo", required = false) String memo,
            @RequestParam(value = "conceptTagIds", required = false) List<Long> conceptTagIds) {
        return ResponseEntity.ok(problemSearchService.searchProblems(
                problemCustomId, title, memo, conceptTagIds));
    }

    @GetMapping("/searchPaging")
    @Operation(
            summary = "문제 페이징 검색",
            description = "문항 ID, 문제명, 개념 태그리스트로 문제를 검색합니다. 개념 태그리스트는 OR 조건으로 검색하며 값이 없으면 쿼리파라미터에서 빼주세요"
    )
    public ResponseEntity<ProblemSearchResponse> searchWithPaging(
            @RequestParam(value = "problemCustomId", required = false) String problemCustomId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "memo", required = false) String memo,
            @RequestParam(value = "conceptTagIds", required = false) List<Long> conceptTagIds,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(problemSearchService.searchProblemsWithPaging(
                problemCustomId, title, memo, conceptTagIds, pageable));
    }
}
