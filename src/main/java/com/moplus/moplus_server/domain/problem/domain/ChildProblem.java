package com.moplus.moplus_server.domain.problem.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ChildProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    Long id;
    @ElementCollection
    @CollectionTable(name = "child_problem_concept", joinColumns = @JoinColumn(name = "concept_tag_id"))
    Set<Long> conceptTagIds;
    private String problemImageUrl;
    private String answer;

    public ChildProblem(String problemImageUrl, String answer, Set<Long> conceptTagIds) {
        this.problemImageUrl = problemImageUrl;
        this.answer = answer;
        this.conceptTagIds = conceptTagIds;
    }
}
