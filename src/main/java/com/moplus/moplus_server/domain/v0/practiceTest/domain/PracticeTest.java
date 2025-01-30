package com.moplus.moplus_server.domain.v0.practiceTest.domain;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.Subject;
import com.moplus.moplus_server.domain.v0.practiceTest.dto.admin.request.PracticeTestRequest;
import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Duration;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "practice_test")
@NoArgsConstructor
public class PracticeTest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "practice_test_id")
    Long id;

    private String name;
    private String round;
    private String provider;

    private long viewCount = 0L;
    private int solvesCount = 0;
    private int publicationYear;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    private Duration averageSolvingTime = Duration.ZERO;

    @Builder
    public PracticeTest(String name, String round, String provider, long viewCount, int solvesCount,
                        int publicationYear, Subject subject, Duration averageSolvingTime) {
        this.name = name;
        this.round = round;
        this.provider = provider;
        this.viewCount = viewCount;
        this.solvesCount = solvesCount;
        this.publicationYear = publicationYear;
        this.subject = subject;
        this.averageSolvingTime = averageSolvingTime;
    }

    public void updateByPracticeTestRequest(PracticeTestRequest request) {
        this.name = request.getName();
        this.round = request.getRound();
        this.provider = request.getProvider();
        this.publicationYear = request.getPublicationYear();
        this.subject = Subject.valueOf(request.getSubject());
    }

    public void plus1ViewCount() {
        this.viewCount += 1;
    }

    public void plus1SolvesCount() {
        this.solvesCount += 1;
    }

    public void updateAverageSolvingTime(Duration averageSolvingTime) {
        this.averageSolvingTime = averageSolvingTime;
    }
}
