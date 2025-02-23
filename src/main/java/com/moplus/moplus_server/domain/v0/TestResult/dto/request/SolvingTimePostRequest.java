package com.moplus.moplus_server.domain.v0.TestResult.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Duration;
import java.time.LocalTime;

public record SolvingTimePostRequest(
        @Schema(example = "PT1H10M")
        String solvingTime
) {

}
