package com.moplus.moplus_server.client.submit.dto.response;

import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record AllProblemGetResponse(
        Long publishId,
        LocalDate date,
        DayProgress progress,
        List<ProblemSubmitStatus> problemStatuses,
        String mainProblemImageUrl
) {
    public static AllProblemGetResponse of(Long publishId, LocalDate date, DayProgress progress,
                                           List<ProblemSubmitStatus> problemStatuses, String mainProblemImageUrl) {
        return AllProblemGetResponse.builder()
                .publishId(publishId)
                .date(date)
                .progress(progress)
                .problemStatuses(problemStatuses)
                .mainProblemImageUrl(mainProblemImageUrl)
                .build();
    }
}
