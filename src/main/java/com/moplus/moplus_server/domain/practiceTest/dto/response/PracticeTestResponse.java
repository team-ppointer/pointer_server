package com.moplus.moplus_server.domain.practiceTest.dto.response;

import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import lombok.Builder;

@Builder
public record PracticeTestResponse(
    Long id,
    String name,
    String round,
    String provider,
    String subject
) {
    public static PracticeTestResponse from(PracticeTest practiceTest) {
        return PracticeTestResponse.builder()
            .id(practiceTest.getId())
            .name(practiceTest.getName())
            .provider(practiceTest.getProvider())
            .round(practiceTest.getRound())
            .subject(practiceTest.getSubject().getValue())
            .build();
    }
}
