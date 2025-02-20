package com.moplus.moplus_server.domain.problemset.dto.response;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemSetGetResponse(
        Long id,
        String title,
        ProblemSetConfirmStatus confirmStatus,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        List<LocalDate> publishedDates,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        List<ProblemSummaryResponse> problemSummaries
) {
    public static ProblemSetGetResponse of(ProblemSet problemSet, List<LocalDate> publishedDates,
                                           List<ProblemSummaryResponse> problemSummaries) {

        return ProblemSetGetResponse.builder()
                .id(problemSet.getId())
                .title(problemSet.getTitle().getValue())
                .confirmStatus(problemSet.getConfirmStatus())
                .publishedDates(publishedDates)
                .problemSummaries(problemSummaries)
                .build();
    }
}
