package com.moplus.moplus_server.domain.DetailResultApplication.dto.request;

import com.moplus.moplus_server.domain.DetailResultApplication.entity.DetailResultApplication;

public record DetailResultApplicationPostRequest(
    Long testResultId,
    String name,
    String phoneNumber
) {

    public DetailResultApplication toEntity() {
        return DetailResultApplication.builder()
            .testResultId(testResultId)
            .name(name)
            .phoneNumber(phoneNumber)
            .build();
    }
}
