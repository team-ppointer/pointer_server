package com.moplus.moplus_server.domain.concept.controller;

import com.moplus.moplus_server.domain.concept.dto.response.ConceptTagResponse;
import com.moplus.moplus_server.domain.concept.repository.ConceptTagRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "문항", description = "문항 관련 API")
@RestController
@RequestMapping("/api/v1/conceptTags")
@RequiredArgsConstructor
public class ConceptTagController {

    ConceptTagRepository conceptTagRepository;

    @GetMapping("")
    @Operation(summary = "모든 개념 태그 리스트 조회")
    public ResponseEntity<List<ConceptTagResponse>> getConceptTags(
    ) {
        List<ConceptTagResponse> responses = conceptTagRepository.findAll().stream()
                .map(ConceptTagResponse::of)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
