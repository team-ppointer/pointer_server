package com.moplus.moplus_server.admin.problem.controller;

import com.moplus.moplus_server.domain.problem.domain.problem.ProblemImageType;
import com.moplus.moplus_server.admin.problem.dto.response.PresignedUrlResponse;
import com.moplus.moplus_server.domain.problem.service.ImageUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이미지 업로드 API", description = "이미지 업로드 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @Operation(summary = "이미지 업로드를 위한 presigned URL 발급")
    @GetMapping("/problem/{problemId}/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getProblemImagePresignedUrl(
            @PathVariable("problemId") Long problemId,
            @RequestParam(value = "image-type") ProblemImageType imageType) {
        String presignedUrl = imageUploadService.generateProblemImagePresignedUrl(problemId, imageType);
        return ResponseEntity.ok(PresignedUrlResponse.of(presignedUrl));
    }

    @Operation(summary = "이미지 업로드 완료 후 URL 조회")
    @GetMapping("/{fileName}")
    public ResponseEntity<String> getImageUrl(
            @PathVariable("fileName") String fileName) {
        String imageUrl = imageUploadService.getImageUrl(fileName);
        return ResponseEntity.ok(imageUrl);
    }
}
