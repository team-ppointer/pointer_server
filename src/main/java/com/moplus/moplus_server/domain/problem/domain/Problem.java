package com.moplus.moplus_server.domain.problem.domain;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    String practiceTestId;
    int number;
    int answer;
    String comment;
    String mainProblemImageUrl;
    String mainAnalysisImageUrl;
    String readingTipImageUrl;
    String seniorTipImageUrl;
    String prescriptionImageUrl;

    @ElementCollection
    @CollectionTable(name = "problem_concept", joinColumns = @JoinColumn(name = "concept_tag_id"))
    Set<Long> conceptTagIds;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "problem_id")
    private List<ChildProblem> childProblems = new ArrayList<>();

    public Problem(String practiceTestId, int number, int answer, String comment, String mainProblemImageUrl,
                   String mainAnalysisImageUrl, String readingTipImageUrl, String seniorTipImageUrl,
                   String prescriptionImageUrl, Set<Long> conceptTagIds) {
        this.practiceTestId = practiceTestId;
        this.number = number;
        this.answer = answer;
        this.comment = comment;
        this.mainProblemImageUrl = mainProblemImageUrl;
        this.mainAnalysisImageUrl = mainAnalysisImageUrl;
        this.readingTipImageUrl = readingTipImageUrl;
        this.seniorTipImageUrl = seniorTipImageUrl;
        this.prescriptionImageUrl = prescriptionImageUrl;
        this.conceptTagIds = conceptTagIds;
    }
}
