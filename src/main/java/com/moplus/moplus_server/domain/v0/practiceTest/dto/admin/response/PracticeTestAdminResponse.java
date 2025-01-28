package com.moplus.moplus_server.domain.v0.practiceTest.dto.admin.response;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.PracticeTest;
import lombok.Builder;

@Builder
public record PracticeTestAdminResponse(
        Long id,
        String name,
        String round,
        String provider,
        String subject
) {
    public static PracticeTestAdminResponse from(PracticeTest practiceTest) {
        return PracticeTestAdminResponse.builder()
                .id(practiceTest.getId())
                .name(practiceTest.getName())
                .provider(practiceTest.getProvider())
                .round(practiceTest.getRound())
                .subject(practiceTest.getSubject().getValue())
                .build();
    }
}
