package com.moplus.moplus_server.domain.problemset.controller;


import com.moplus.moplus_server.domain.problemset.dto.response.ProblemSetSearchGetResponse;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetSearchRepositoryCustom;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/problemSet")
@RequiredArgsConstructor
public class ProblemSetSearchController {

    private final ProblemSetSearchRepositoryCustom problemSetSearchRepository;

    @GetMapping("/search")
    @Operation(
            summary = "문항세트 검색",
            description = "문항세트 타이틀, 문항세트 내 포함된 개념태그, 문항세트 내 포함된 문항 타이틀로 검색합니다. 발행상태는 발행이면 CONFIRMED, 아니면 NOT_CONFIRMED 입니다."
    )
    public ResponseEntity<List<ProblemSetSearchGetResponse>> search(
            @RequestParam(value = "problemSetTitle", required = false) String problemSetTitle,
            @RequestParam(value = "problemTitle", required = false) String problemTitle,
            @RequestParam(value = "conceptTagNames", required = false) List<String> conceptTagNames
    ) {
        List<ProblemSetSearchGetResponse> problemSets = problemSetSearchRepository.search(problemSetTitle, problemTitle, conceptTagNames);
        return ResponseEntity.ok(problemSets);
    }

    @GetMapping("/confirm/search")
    @Operation(
            summary = "발행용 문항세트 검색",
            description = "문항세트 타이틀, 문항세트 내 포함된 개념태그, 문항세트 내 포함된 문항 타이틀로 검색합니다. 발행상태가 CONFIRMED 문항세트만 조회됩니다.."
    )
    public ResponseEntity<List<ProblemSetSearchGetResponse>> confirmSearch(
            @RequestParam(value = "problemSetTitle", required = false) String problemSetTitle,
            @RequestParam(value = "problemTitle", required = false) String problemTitle,
            @RequestParam(value = "conceptTagNames", required = false) List<String> conceptTagNames
    ) {
        List<ProblemSetSearchGetResponse> problemSets = problemSetSearchRepository.confirmSearch(problemSetTitle, problemTitle, conceptTagNames);
        return ResponseEntity.ok(problemSets);
    }
}
