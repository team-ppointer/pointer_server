package com.moplus.moplus_server.domain.TestResult.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record RatingGetResponse(
        @Schema(description = "제공자 회사", example = "대성마이맥")
        String provider,
        List<RatingDetailsResponse> ratingDetails
) {
}
