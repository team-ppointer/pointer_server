package com.moplus.moplus_server.domain.TestResult.entity;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class TestResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_result_id")
    Long id;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int solvingHour;

    @Column(nullable = false)
    private int solvingMinutes;

    @Column(nullable = false)
    private Long practiceTestId;

    @Builder
    public TestResult(int score, int solvingHour, int solvingMinutes, Long practiceTestId) {
        this.score = score;
        this.solvingHour = solvingHour;
        this.solvingMinutes = solvingMinutes;
        this.practiceTestId = practiceTestId;
    }

    public static TestResult fromPracticeTestId(Long practiceTestId) {
        return TestResult.builder()
            .practiceTestId(practiceTestId)
            .build();
    }

    public void addScore(int score) {
        this.score = score;
    }
}
