package com.moplus.moplus_server.client.problem.dto.response;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemFeedProgressesGetResponse(
        int number,
        ProblemSubmitStatus status,
        List<ChildProblemSubmitStatus> childProblemStatuses
) {
    public static ProblemFeedProgressesGetResponse of(ProblemSubmitStatus status,
                                                      List<ChildProblemSubmitStatus> childProblemStatuses, int number) {
        return ProblemFeedProgressesGetResponse.builder()
                .number(number)
                .status(status)
                .childProblemStatuses(childProblemStatuses)
                .build();
    }
}
