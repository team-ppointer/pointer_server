package com.moplus.moplus_server.client.submit.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProblemSubmitUpdateRequest(
        @NotNull(message = "발행 ID는 필수입니다.")
        Long publishId,
        @NotNull(message = "문항 ID는 필수입니다.")
        Long problemId,
        String answer
) {
}
