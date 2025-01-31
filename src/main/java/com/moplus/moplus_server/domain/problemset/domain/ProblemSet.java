package com.moplus.moplus_server.domain.problemset.domain;

import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
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

    private String name;
    private boolean isDeleted;
    private boolean isConfirmed;

    @ElementCollection
    @CollectionTable(name = "problem_set_problems", joinColumns = @JoinColumn(name = "problem_set_id"))
    @Column(name = "problem_id")
    @OrderColumn(name = "sequence")
    private List<ProblemId> problemIds = new ArrayList<>();

    @Builder
    public ProblemSet(String name) {
        this.name = name;
        this.isDeleted = false;
        this.isConfirmed = false;
    }

    public void updateProblemOrder(List<ProblemId> newProblems) {
        this.problemIds.clear();
        this.problemIds.addAll(newProblems);
    }

    public void deleteProblemSet() {
        this.isDeleted = true;
    }

    public void toggleConfirm(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public void updateProblemSet(String name, List<ProblemId> newProblems) {
        this.name = name;
        this.problemIds = newProblems;
    }
}
