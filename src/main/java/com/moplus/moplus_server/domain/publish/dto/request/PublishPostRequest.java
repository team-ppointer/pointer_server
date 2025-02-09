package com.moplus.moplus_server.domain.publish.dto.request;

import com.moplus.moplus_server.domain.publish.domain.Publish;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import java.time.LocalDate;

public record PublishPostRequest(
        LocalDate publishedDate,
        Long problemSetId
) {
    public PublishPostRequest {
        validatePublishedDate(publishedDate);
    }

    public Publish toEntity() {
        return Publish.builder()
                .publishedDate(this.publishedDate)
                .problemSetId(this.problemSetId)
                .build();
    }

    private static void validatePublishedDate(LocalDate publishedDate) {
        // 발행 시점 다음날부터 발행 가능
        if (publishedDate.isBefore(LocalDate.now().plusDays(1))) {
            throw new InvalidValueException(ErrorCode.INVALID_DATE_ERROR);
        }
    }
}
