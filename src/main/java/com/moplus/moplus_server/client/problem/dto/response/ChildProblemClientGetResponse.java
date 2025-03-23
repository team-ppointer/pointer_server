package com.moplus.moplus_server.client.problem.dto.response;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import lombok.Builder;

@Builder
public record ChildProblemClientGetResponse(
        int problemNumber,
        int childProblemNumber,
        String imageUrl,
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
