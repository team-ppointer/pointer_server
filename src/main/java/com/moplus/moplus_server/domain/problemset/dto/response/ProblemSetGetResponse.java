package com.moplus.moplus_server.domain.problemset.dto.response;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemSetGetResponse(
        Long id,
        String title,
        ProblemSetConfirmStatus confirmStatus,
        List<ProblemSummaryResponse> problemSummaries
) {
    public static ProblemSetGetResponse of(ProblemSet problemSet, List<ProblemSummaryResponse> problemSummaries) {

        return ProblemSetGetResponse.builder()
                .id(problemSet.getId())
                .title(problemSet.getTitle())
                .confirmStatus(problemSet.getConfirmStatus())
                .problemSummaries(problemSummaries)
                .build();
    }
}
