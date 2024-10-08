package com.moplus.moplus_server.domain.practiceTest.entity;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Duration;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class PracticeTest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "practice_test_id")
    Long id;

    private String name;
    private String round;
    private String provider;
    private Long viewCount = 0L;
    private Integer solvesCount = 0;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @Column(nullable = false)
    private Duration averageSolvingTime = Duration.ZERO;

    @Builder
    public PracticeTest(String name, String round, String provider, Long viewCount, Integer solvesCount,
        Subject subject,
        Duration averageSolvingTime) {
        this.name = name;
        this.round = round;
        this.provider = provider;
        this.viewCount = 0L;
        this.solvesCount = 0;
        this.subject = subject;
        this.averageSolvingTime = Duration.ZERO;
    }

    @Builder


    public void updateName(String name) {
        this.name = name;
    }

    public void updateRound(String round) {
        this.round = round;
    }

    public void updateProvider(String provider) {
        this.provider = provider;
    }

    public void updateSubject(Subject subject) {
        this.subject = subject;
    }

    public void plus1ViewCount() {
        this.viewCount += 1;
    }

    public void plus1SolvesCount() {
        this.solvesCount += 1;
    }
}
