package com.moplus.moplus_server.domain.TestResult.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record RatingDetailsResponse(
        @Schema(description = "등급", example = "1")
        String rating,
        @Schema(description = "원점수", example = "81-82")
        String rawScores,
        @Schema(description = "표준점수", example = "132")
        int standardScores,
        @Schema(description = "백분위", example = "96")
        int percentiles
) {
}
