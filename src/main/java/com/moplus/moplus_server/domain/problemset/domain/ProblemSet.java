package com.moplus.moplus_server.domain.problemset.domain;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.global.common.BaseEntity;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemSet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_set_id")
    Long id;

    private String title;
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private ProblemSetConfirmStatus confirmStatus;

    @ElementCollection
    @CollectionTable(name = "problem_set_problems", joinColumns = @JoinColumn(name = "problem_set_id"))
    @Column(name = "problem_id")
    @OrderColumn(name = "sequence")
    private List<ProblemId> problemIds = new ArrayList<>();

    @Builder
    public ProblemSet(String title, List<ProblemId> problemIds) {
        this.title = title;
        this.isDeleted = false;
        this.confirmStatus = ProblemSetConfirmStatus.NOT_CONFIRMED;
        this.problemIds = problemIds;
    }

    public void updateProblemOrder(List<ProblemId> newProblems) {
        this.problemIds = new ArrayList<>(newProblems);
    }

    public void deleteProblemSet() {
        this.isDeleted = true;
    }

    public void toggleConfirm(List<Problem> problems) {
        if(this.confirmStatus == ProblemSetConfirmStatus.NOT_CONFIRMED){
            // 문항 유효성 검사
            for (Problem problem : problems) {
                if (!problem.isValid()) {
                    throw new InvalidValueException(ErrorCode.INVALID_CONFIRM_PROBLEM);
                }
            }
        }
        this.confirmStatus = this.confirmStatus.toggle();
    }

    public void updateProblemSet(String title, List<ProblemId> newProblems) {
        this.title = title;
        this.problemIds = newProblems;
    }
}
