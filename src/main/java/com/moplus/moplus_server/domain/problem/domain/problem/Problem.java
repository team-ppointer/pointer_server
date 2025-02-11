package com.moplus.moplus_server.domain.problem.domain.problem;

import com.moplus.moplus_server.domain.problem.domain.Answer;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Problem extends BaseEntity {

    @Embedded
    ProblemAdminId problemAdminId;
    Long practiceTestId;
    int number;
    @Enumerated(EnumType.STRING)
    ProblemType problemType;
    String title;
    @Embedded
    Answer answer;
    String memo;
    String mainProblemImageUrl;
    String mainAnalysisImageUrl;
    String readingTipImageUrl;
    String seniorTipImageUrl;
    String prescriptionImageUrl;
    @ElementCollection
    @CollectionTable(name = "problem_concept", joinColumns = @JoinColumn(name = "problem_id"))
    @Column(name = "concept_tag_id")
    Set<Long> conceptTagIds;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    private boolean isConfirmed;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    @OrderBy("sequence ASC")
    private List<ChildProblem> childProblems = new ArrayList<>();

    @Builder
    public Problem(List<ChildProblem> childProblems, boolean isConfirmed, AnswerType answerType,
                   Set<Long> conceptTagIds,
                   String prescriptionImageUrl, String seniorTipImageUrl, String readingTipImageUrl,
                   String mainAnalysisImageUrl, String mainProblemImageUrl, String memo, String answer, String title,
                   ProblemType problemType, int number, PracticeTestTag practiceTestTag,
                   ProblemAdminId problemAdminId) {
        this.childProblems = childProblems;
        this.isConfirmed = isConfirmed;
        this.answerType = AnswerType.getTypeForProblem(practiceTestTag.getSubject().getValue(), number);
        this.conceptTagIds = conceptTagIds;
        this.prescriptionImageUrl = prescriptionImageUrl;
        this.seniorTipImageUrl = seniorTipImageUrl;
        this.readingTipImageUrl = readingTipImageUrl;
        this.mainAnalysisImageUrl = mainAnalysisImageUrl;
        this.mainProblemImageUrl = mainProblemImageUrl;
        this.memo = memo;
        this.answer = new Answer(answer, this.answerType);
        this.title = title;
        this.problemType = problemType;
        this.number = number;
        this.practiceTestId = practiceTestTag.getId();
        this.problemAdminId = problemAdminId;
    }

    public String getAnswer() {
        return answer.getValue();
    }

    public void addChildProblem(List<ChildProblem> inputChildProblems) {
        List<ChildProblem> mutableChildProblems = new ArrayList<>(inputChildProblems);
        mutableChildProblems.sort(Comparator.comparingInt(ChildProblem::getSequence));
        mutableChildProblems.forEach(childProblems::add);
        mutableChildProblems.forEach(childProblem -> conceptTagIds.addAll(childProblem.getConceptTagIds()));
    }

    public void update(Problem inputProblem) {
        this.conceptTagIds = new HashSet<>(inputProblem.getConceptTagIds());
        this.number = inputProblem.getNumber();
        this.answer = new Answer(inputProblem.getAnswer(), this.answerType);
        this.memo = inputProblem.getMemo();
        this.mainProblemImageUrl = inputProblem.getMainProblemImageUrl();
        this.mainAnalysisImageUrl = inputProblem.getMainAnalysisImageUrl();
        this.readingTipImageUrl = inputProblem.getReadingTipImageUrl();
        this.seniorTipImageUrl = inputProblem.getSeniorTipImageUrl();
        this.prescriptionImageUrl = inputProblem.getPrescriptionImageUrl();
    }

    public void updateChildProblem(List<ChildProblem> inputChildProblems) {
        inputChildProblems.forEach(childProblem -> {
            this.childProblems.stream()
                    .filter(existingChildProblem -> existingChildProblem.getId().equals(childProblem.getId()))
                    .findFirst()
                    .ifPresentOrElse(
                            existingChildProblem -> {
                                existingChildProblem.update(childProblem);
                                conceptTagIds.addAll(existingChildProblem.getConceptTagIds());
                            },
                            () -> {
                                childProblems.add(childProblem);
                                conceptTagIds.addAll(childProblem.getConceptTagIds());
                            }
                    );
        });
    }

    public void deleteChildProblem(List<Long> deleteChildProblems) {
        childProblems.removeIf(childProblem -> deleteChildProblems.contains(childProblem.getId()));
    }

    public boolean isValid() {
        return answer != null && !answer.getValue().isEmpty()
                && practiceTestId != null
                && memo != null && !memo.isEmpty()
                && readingTipImageUrl != null && !readingTipImageUrl.isEmpty()
                && seniorTipImageUrl != null && !seniorTipImageUrl.isEmpty()
                && prescriptionImageUrl != null && !prescriptionImageUrl.isEmpty()
                && mainProblemImageUrl != null && !mainProblemImageUrl.isEmpty()
                && mainAnalysisImageUrl != null && !mainAnalysisImageUrl.isEmpty();
    }
}