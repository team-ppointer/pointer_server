package com.moplus.moplus_server.client.problem.dto.response;

import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record PublishClientGetResponse(
        Long publishId,
        LocalDate date,
        String title,
        List<ProblemClientGetResponse> problems
) {

    public static PublishClientGetResponse of(Publish publish, ProblemSet problemSet,
                                              List<ProblemClientGetResponse> problems) {
        return PublishClientGetResponse.builder()
                .publishId(publish.getId())
                .date(publish.getPublishedDate())
                .title(problemSet.getTitle().getValue())
                .problems(problems)
                .build();
    }
}
