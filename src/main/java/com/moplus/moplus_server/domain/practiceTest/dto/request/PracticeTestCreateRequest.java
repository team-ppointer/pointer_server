package com.moplus.moplus_server.domain.practiceTest.dto.request;

import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.entity.Subject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PracticeTestCreateRequest {
    private Long id;
    private String name;
    private String round;
    private String provider;
    private String publicationYear;
    private String subject;
    private Integer[] rawScores;
    private Integer[] standardScores;
    private Integer[] percentiles;

    public PracticeTestCreateRequest(Long id, String name, String round, String provider, String publicationYear,
                                     String subject, Integer[] rawScores, Integer[] standardScores,
                                     Integer[] percentiles) {
        this.id = id;
        this.name = name;
        this.round = round;
        this.provider = provider;
        this.publicationYear = publicationYear;
        this.subject = subject;
        this.rawScores = rawScores;
        this.standardScores = standardScores;
        this.percentiles = percentiles;
    }

    public PracticeTestCreateRequest(String subject, String publicationYear, String provider, String round, String name,
                                     Long id) {
        this.subject = subject;
        this.publicationYear = publicationYear;
        this.provider = provider;
        this.round = round;
        this.name = name;
        this.id = id;
    }

    public PracticeTest toEntity() {
        return PracticeTest.builder()
                .name(this.name)
                .provider(this.provider)
                .round(this.round)
                .publicationYear(this.publicationYear)
                .subject(Subject.fromValue(this.subject))
                .build();
    }
}
