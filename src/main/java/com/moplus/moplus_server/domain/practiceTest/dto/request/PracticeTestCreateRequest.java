package com.moplus.moplus_server.domain.practiceTest.dto.request;


import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.entity.Subject;
import lombok.Builder;

public record PracticeTestCreateRequest(
    Long id,
    String name,
    String round,
    String provider,
    String publicationYear,
    String subject
) {
    public PracticeTest toEntity(){
        return PracticeTest.builder()
            .name(this.name)
            .provider(this.provider)
            .round(this.round)
            .publicationYear(this.publicationYear)
            .subject(Subject.fromValue(this.subject))
            .build();
    }
}
