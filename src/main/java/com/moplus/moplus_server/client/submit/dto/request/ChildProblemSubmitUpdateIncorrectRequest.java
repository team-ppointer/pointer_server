package com.moplus.moplus_server.client.submit.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChildProblemSubmitUpdateIncorrectRequest(
        @NotNull(message = "발행 ID는 필수입니다.")
        Long publishId,
        @NotNull(message = "새끼문항 ID는 필수입니다.")
        Long childProblemId
) {
}
