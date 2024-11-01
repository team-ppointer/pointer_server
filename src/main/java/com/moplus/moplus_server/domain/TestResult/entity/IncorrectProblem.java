package com.moplus.moplus_server.domain.TestResult.entity;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class IncorrectProblem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "incorrect_problem_id")
    Long id;

    private Long problemId;
    private Long practiceTestId;
    private String incorrectAnswer;
    private String problemNumber;
    private int point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_result_id")
    private TestResult testResult;

    @Builder
    public IncorrectProblem(Long problemId, Long practiceTestId, String incorrectAnswer, String problemNumber,
        int point,
        TestResult testResult) {
        this.problemId = problemId;
        this.practiceTestId = practiceTestId;
        this.incorrectAnswer = incorrectAnswer;
        this.problemNumber = problemNumber;
        this.point = point;
        this.testResult = testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public void setPracticeTestId(Long practiceTestId) {
        this.practiceTestId = practiceTestId;
    }
}
