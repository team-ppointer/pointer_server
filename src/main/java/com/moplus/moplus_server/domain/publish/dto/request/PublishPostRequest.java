package com.moplus.moplus_server.domain.publish.dto.request;

import com.moplus.moplus_server.domain.publish.domain.Publish;
import java.time.LocalDate;

public record PublishPostRequest(
        LocalDate publishedDate,
        Long problemSetId
) {
    public Publish toEntity() {
        return Publish.builder()
                .publishedDate(this.publishedDate)
                .problemSetId(this.problemSetId)
                .build();
    }
}
