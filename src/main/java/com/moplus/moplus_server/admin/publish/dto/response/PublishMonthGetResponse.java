package com.moplus.moplus_server.admin.publish.dto.response;

import com.moplus.moplus_server.admin.publish.domain.Publish;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PublishMonthGetResponse(
        Long publishId,
        LocalDate date,
        PublishProblemSetResponse problemSetInfo
) {
    public static PublishMonthGetResponse of(Publish publish, PublishProblemSetResponse problemSetInfos) {
        return PublishMonthGetResponse.builder()
                .publishId(publish.getId())
                .date(publish.getPublishedDate())
                .problemSetInfo(problemSetInfos)
                .build();
    }
}
