package com.moplus.moplus_server.domain.v0.practiceTest.domain;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ProblemForTest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_for_test_id")
    Long id;
    double correctRate;
    @Enumerated(EnumType.STRING)
    ProblemRating problemRating;
    private String problemNumber;
    @Enumerated(EnumType.STRING)
    private AnswerFormat answerFormat;
    private String answer;
    private int point;
    private Long incorrectNum;
    private String conceptType;
    private String unit;
    private String subunit;
    @ManyToOne()
    @JoinColumn(name = "practice_test_id")
    private PracticeTest practiceTest;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "problem_image_id")
    private ProblemImageForTest image;

    @Builder
    public ProblemForTest(String problemNumber, AnswerFormat answerFormat, String answer, int point, Long incorrectNum,
                          String conceptType, String unit, String subunit, double correctRate,
                          ProblemRating problemRating,
                          PracticeTest practiceTest) {
        this.problemNumber = problemNumber;
        this.answerFormat = answerFormat;
        this.answer = answer;
        this.point = point;
        this.incorrectNum = incorrectNum;
        this.conceptType = conceptType;
        this.unit = unit;
        this.subunit = subunit;
        this.correctRate = correctRate;
        this.problemRating = problemRating;
        this.practiceTest = practiceTest;
    }


    public void addImage(ProblemImageForTest image) {
        this.image = image;
    }

    public void calculateProblemRating() {
        this.problemRating = ProblemRating.findProblemRating(this);
    }

    public void updateAnswer(String answer) {
        this.answer = answer;
    }

    public void updatePoint(int point) {
        this.point = point;
    }

    public void updateCorrectRate(double correctRate) {
        this.correctRate = correctRate;
    }
}
