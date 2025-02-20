package com.moplus.moplus_server.domain.publish.dto.response;

import com.moplus.moplus_server.domain.publish.domain.Publish;
import lombok.Builder;

@Builder
public record PublishMonthGetResponse(
        Long publishId,
        int day,
        PublishProblemSetResponse problemSetInfo
) {
    public static PublishMonthGetResponse of(Publish publish, PublishProblemSetResponse problemSetInfos) {
        return PublishMonthGetResponse.builder()
                .publishId(publish.getId())
                .day(publish.getPublishedDate().getDayOfMonth())
                .problemSetInfo(problemSetInfos)
                .build();
    }
}
