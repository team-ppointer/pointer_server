package com.moplus.moplus_server.domain.publish.dto.response;

import lombok.Builder;

@Builder
public record PublishMonthGetResponse(
        int day,
        PublishProblemSetResponse problemSetInfo
) {
    public static PublishMonthGetResponse of(int day, PublishProblemSetResponse problemSetInfos) {

        return PublishMonthGetResponse.builder()
                .day(day)
                .problemSetInfo(problemSetInfos)
                .build();
    }
}
