package com.moplus.moplus_server.domain.practiceTest.dto.response;

import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Builder;

@Builder
public record PracticeTestGetResponse(
    Long id,
    String name,
    String round,
    String provider,
    String subject,
    Long viewCount,
    Integer totalSolvesCount
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
