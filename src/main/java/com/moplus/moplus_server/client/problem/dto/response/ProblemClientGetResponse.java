package com.moplus.moplus_server.client.problem.dto.response;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemClientGetResponse(
        int number,
        String imageUrl,
        Integer recommendedMinute,
        Integer recommendedSecond,
        ProblemSubmitStatus status,
        List<ChildProblemSubmitStatus> childProblemStatuses
) {
    public static ProblemClientGetResponse of(Problem problem, ProblemSubmitStatus status,
                                              List<ChildProblemSubmitStatus> childProblemStatuses, int number) {
        return ProblemClientGetResponse.builder()
                .number(number)
                .imageUrl(problem.getMainProblemImageUrl())
                .status(status)
                .childProblemStatuses(childProblemStatuses)
                .recommendedMinute(problem.getRecommendedTime().getMinute())
                .recommendedSecond(problem.getRecommendedTime().getSecond())
                .build();
    }
}
