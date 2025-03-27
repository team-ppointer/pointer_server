package com.moplus.moplus_server.client.submit.dto.response;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ChildProblemSubmitUpdateResponse(
        @NotNull(message = "새끼문항제출 상태는 필수입니다.")
        ChildProblemSubmitStatus status,
        @NotNull(message = "새끼문항 정답은 필수입니다.")
        String answer
) {
    public static ChildProblemSubmitUpdateResponse of(ChildProblemSubmitStatus status, String answer) {
        return ChildProblemSubmitUpdateResponse.builder()
                .status(status)
                .answer(answer)
                .build();
    }
}
