package com.moplus.moplus_server.client.problem.dto.response;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ChildProblemClientGetResponse(
        @NotNull(message = "문항번호는 필수입니다.")
        int problemNumber,
        @NotNull(message = "새끼문항번호는 필수입니다.")
        int childProblemNumber,
        @NotNull(message = "이미지URL은 필수입니다.")
        String imageUrl,
        @NotNull(message = "새끼문항 제출상태는 필수입니다.")
        ChildProblemSubmitStatus status
) {
    public static ChildProblemClientGetResponse of(int problemNumber, int childProblemNumber, String imageUrl,
                                                   ChildProblemSubmitStatus status
    ) {
        return ChildProblemClientGetResponse.builder()
                .problemNumber(problemNumber)
                .childProblemNumber(childProblemNumber)
                .imageUrl(imageUrl)
                .status(status)
                .build();
    }
}
