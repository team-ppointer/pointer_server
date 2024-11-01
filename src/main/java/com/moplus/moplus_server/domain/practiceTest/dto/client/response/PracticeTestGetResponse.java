package com.moplus.moplus_server.domain.practiceTest.dto.client.response;

import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import lombok.Builder;

@Builder
public record PracticeTestGetResponse(
    Long id,
    String name,
    String round,
    String provider,
    String subject,
    long viewCount,
    int totalSolvesCount
) {
    public static PracticeTestGetResponse from(PracticeTest practiceTest) {
        return PracticeTestGetResponse.builder()
            .id(practiceTest.getId())
            .name(practiceTest.getName())
            .provider(practiceTest.getProvider())
            .round(practiceTest.getRound())
            .subject(practiceTest.getSubject().getValue())
            .viewCount(practiceTest.getViewCount())
            .totalSolvesCount(practiceTest.getSolvesCount())
            .build();
    }
}
