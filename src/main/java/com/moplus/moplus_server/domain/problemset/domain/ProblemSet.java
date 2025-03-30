package com.moplus.moplus_server.domain.problemset.domain;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.global.common.BaseEntity;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import com.moplus.moplus_server.global.error.exception.ProblemSetToggleException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
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
import org.hibernate.annotations.SoftDelete;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete
public class ProblemSet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_set_id")
    Long id;

    @Embedded
    private Title title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemSetConfirmStatus confirmStatus;

    @ElementCollection
    @CollectionTable(name = "problem_set_problems", joinColumns = @JoinColumn(name = "problem_set_id"))
    @OrderColumn(name = "sequence")
    @Column(name = "problem_id", nullable = false)
    private List<Long> problemIds = new ArrayList<>();

    @Builder
    public ProblemSet(String title, List<Long> problemIds) {
        this.title = new Title(title);
        this.confirmStatus = ProblemSetConfirmStatus.NOT_CONFIRMED;
        this.problemIds = problemIds;
    }

    public static ProblemSet ofEmptyProblemSet() {
        return ProblemSet.builder()
                .title("")
                .problemIds(new ArrayList<>())
                .build();
    }

    public void updateProblemOrder(List<Long> newProblems) {
        this.problemIds = new ArrayList<>(newProblems);
    }

    public void toggleConfirm(List<Problem> problems) {
        if (this.confirmStatus == ProblemSetConfirmStatus.NOT_CONFIRMED) {
            if (problems.isEmpty()) {
                throw new InvalidValueException(ErrorCode.EMPTY_PROBLEMS_ERROR);
            }
            List<String> invalidProblemIds = problems.stream()
                    .filter(problem -> !problem.isValid())
                    .map(Problem::getProblemCustomId)
                    .toList();
            if (!invalidProblemIds.isEmpty()) {
                String message = ErrorCode.INVALID_CONFIRM_PROBLEM.getMessage() +
                        String.join("번 ", invalidProblemIds) + "번";
                throw new ProblemSetToggleException(message);
            }
        }
        this.confirmStatus = this.confirmStatus.toggle();
    }

    public void updateProblemSet(String title, List<Long> newProblems) {
        this.title = new Title(title);
        this.problemIds = newProblems;
    }

    public boolean isConfirmed() {
        return this.confirmStatus == ProblemSetConfirmStatus.CONFIRMED;
    }

    public boolean isProblemsChanged(List<Long> newProblems) {
        if (this.problemIds.size() != newProblems.size()) {
            return true;
        }
        for (int i = 0; i < this.problemIds.size(); i++) {
            if (!this.problemIds.get(i).equals(newProblems.get(i))) {
                return true;
            }
        }
        return false;
    }

}
