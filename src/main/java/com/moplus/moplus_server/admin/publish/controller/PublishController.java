package com.moplus.moplus_server.admin.publish.controller;

import com.moplus.moplus_server.admin.publish.dto.request.PublishPostRequest;
import com.moplus.moplus_server.admin.publish.dto.response.PublishMonthGetResponse;
import com.moplus.moplus_server.admin.publish.service.PublishDeleteService;
import com.moplus.moplus_server.admin.publish.service.PublishGetService;
import com.moplus.moplus_server.admin.publish.service.PublishSaveService;
import com.moplus.moplus_server.global.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "발행", description = "발행 관련 API")
@RestController
@RequestMapping("/api/v1/publish")
@RequiredArgsConstructor
public class PublishController {

    private final PublishGetService publishGetService;
    private final PublishSaveService publishSaveService;
    private final PublishDeleteService publishDeleteService;

    @GetMapping("/{year}/{month}")
    @Operation(summary = "연월별 발행 조회", description = "연월별로 발행된 세트들을 조회합니다.")
    public ResponseEntity<List<PublishMonthGetResponse>> getPublishMonth(
            @PathVariable int year,
            @PathVariable int month
    ) {
        return ResponseEntity.ok(publishGetService.getPublishMonth(year, month));
    }

    @PostMapping("")
    @Operation(summary = "발행 생성하기", description = "특정 날짜에 문항세트를 발행합니다.")
    public ResponseEntity<IdResponse> postPublish(
            @Valid @RequestBody PublishPostRequest request
    ) {
        return ResponseEntity.ok(new IdResponse(publishSaveService.createPublish(request)));
    }

    @DeleteMapping("/{publishId}")
    @Operation(summary = "발행 삭제", description = "발행을 삭제합니다.")
    public ResponseEntity<Void> deleteProblemSet(
            @PathVariable Long publishId
    ) {
        publishDeleteService.deletePublish(publishId);
        return ResponseEntity.ok(null);
    }
}
