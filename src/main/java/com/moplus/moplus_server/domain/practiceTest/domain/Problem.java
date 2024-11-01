package com.moplus.moplus_server.domain.practiceTest.domain;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Problem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    Long id;

    private String problemNumber;
    @Enumerated(EnumType.STRING)
    private AnswerFormat answerFormat;
    private String answer;
    private int point;
    private Long incorrectNum;
    private String conceptType;
    private String unit;
    private String subunit;
    double correctRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practice_test_id")
    private PracticeTest practiceTest;

    @Builder
    public Problem(String problemNumber, AnswerFormat answerFormat, String answer, int point, Long incorrectNum,
        String conceptType, String unit, String subunit, PracticeTest practiceTest, double correctRate) {
        this.problemNumber = problemNumber;
        this.answerFormat = answerFormat;
        this.answer = answer;
        this.point = point;
        this.incorrectNum = incorrectNum;
        this.conceptType = conceptType;
        this.unit = unit;
        this.subunit = subunit;
        this.practiceTest = practiceTest;
        this.correctRate = correctRate;
    }

    public void updateAnswer(String answer) {
        this.answer = answer;
    }

    public void updatePoint(int point) {
        this.point = point;
    }

    public void updateCorrectRate (double correctRate) {
        this.correctRate = correctRate;
    }
}
