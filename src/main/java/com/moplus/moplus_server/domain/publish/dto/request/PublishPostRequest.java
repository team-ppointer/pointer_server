package com.moplus.moplus_server.domain.publish.dto.request;

import com.moplus.moplus_server.domain.publish.domain.Publish;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record PublishPostRequest(
        @NotNull
        LocalDate publishedDate,
        @NotNull
        Long problemSetId
) {
    public Publish toEntity() {
        return Publish.builder()
                .publishedDate(this.publishedDate)
                .problemSetId(this.problemSetId)
                .build();
    }
}
