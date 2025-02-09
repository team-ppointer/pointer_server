package com.moplus.moplus_server.domain.problemset.dto.response;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemSetGetResponse(
        Long id,
        String title,
        ProblemSetConfirmStatus confirmStatus,
        LocalDate publishedDate,
        List<ProblemSummaryResponse> problemSummaries
) {
    public static ProblemSetGetResponse of(ProblemSet problemSet, LocalDate publishedDate, List<ProblemSummaryResponse> problemSummaries) {

        return ProblemSetGetResponse.builder()
                .id(problemSet.getId())
                .title(problemSet.getTitle().getValue())
                .confirmStatus(problemSet.getConfirmStatus())
                .publishedDate(publishedDate)
                .problemSummaries(problemSummaries)
                .build();
    }
}
