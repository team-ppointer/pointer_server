package com.moplus.moplus_server.admin.publish.dto.response;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import lombok.Builder;

@Builder
public record PublishProblemSetResponse(
        Long id,
        String title
) {
    public static PublishProblemSetResponse of(ProblemSet problemSet) {

        return PublishProblemSetResponse.builder()
                .id(problemSet.getId())
                .title(problemSet.getTitle().getValue())
                .build();
    }
}
