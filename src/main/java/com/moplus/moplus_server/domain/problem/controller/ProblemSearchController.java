package com.moplus.moplus_server.domain.problem.controller;

import com.moplus.moplus_server.domain.problem.dto.response.ProblemSearchGetResponse;
import com.moplus.moplus_server.domain.problem.repository.ProblemSearchRepositoryCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    private final ProblemSearchRepositoryCustom problemSearchRepository;

    @GetMapping("/search")
    @Operation(
            summary = "문제 검색",
            description = "문항 ID, 문제명, 개념 태그리스트로 문제를 검색합니다. 개념 태그리스트는 OR 조건으로 검색하며 값이 없으면 쿼리파라미터에서 빼주세요"
    )
    public ResponseEntity<List<ProblemSearchGetResponse>> search(
            @RequestParam(value = "problemId", required = false) String problemId,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "conceptTagIds", required = false) List<Long> conceptTagIds
    ) {
        List<ProblemSearchGetResponse> problems = problemSearchRepository.search(problemId, comment, conceptTagIds);
        return ResponseEntity.ok(problems);
    }
}
