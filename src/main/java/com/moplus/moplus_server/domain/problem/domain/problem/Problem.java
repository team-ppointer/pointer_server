package com.moplus.moplus_server.domain.problem.domain.problem;

import com.moplus.moplus_server.domain.problem.domain.Answer;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.repository.converter.StringListConverter;
import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem extends BaseEntity {

    @Embedded
    ProblemAdminId problemAdminId;
    Long practiceTestId;
    int number;
    @Enumerated(EnumType.STRING)
    ProblemType problemType;
    @Embedded
    Title title;
    @Embedded
    Answer answer;
    @Embedded
    Difficulty difficulty;

    String memo;
    String mainProblemImageUrl;
    String mainAnalysisImageUrl;
    String mainHandwritingExplanationImageUrl;
    String readingTipImageUrl;
    String seniorTipImageUrl;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    List<String> prescriptionImageUrls;
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
                   Set<Long> conceptTagIds, Integer difficulty, String mainHandwritingExplanationImageUrl,
                   List<String> prescriptionImageUrls, String seniorTipImageUrl, String readingTipImageUrl,
                   String mainAnalysisImageUrl, String mainProblemImageUrl, String memo, String answer, String title,
                   ProblemType problemType, int number, PracticeTestTag practiceTestTag,
                   ProblemAdminId problemAdminId) {
        this.childProblems = childProblems;
        this.isConfirmed = isConfirmed;
        this.answerType = answerType;
        this.conceptTagIds = conceptTagIds;
        this.mainHandwritingExplanationImageUrl = mainHandwritingExplanationImageUrl;
        this.prescriptionImageUrls = prescriptionImageUrls;
        this.seniorTipImageUrl = seniorTipImageUrl;
        this.readingTipImageUrl = readingTipImageUrl;
        this.mainAnalysisImageUrl = mainAnalysisImageUrl;
        this.mainProblemImageUrl = mainProblemImageUrl;
        this.difficulty = new Difficulty(difficulty);
        this.memo = memo;
        this.answer = new Answer(answer, this.answerType);
        this.title = new Title(title);
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
        this.problemAdminId = inputProblem.getProblemAdminId();
        this.practiceTestId = inputProblem.getPracticeTestId();
        this.number = inputProblem.getNumber();
        this.problemType = inputProblem.getProblemType();
        this.title = new Title(inputProblem.getTitle());
        this.answer = new Answer(inputProblem.getAnswer(), inputProblem.getAnswerType());
        this.difficulty = new Difficulty(inputProblem.getDifficulty());
        this.conceptTagIds = new HashSet<>(inputProblem.getConceptTagIds());
        this.memo = inputProblem.getMemo();
        this.mainProblemImageUrl = inputProblem.getMainProblemImageUrl();
        this.mainAnalysisImageUrl = inputProblem.getMainAnalysisImageUrl();
        this.mainHandwritingExplanationImageUrl = inputProblem.getMainHandwritingExplanationImageUrl(); // 추가
        this.readingTipImageUrl = inputProblem.getReadingTipImageUrl();
        this.seniorTipImageUrl = inputProblem.getSeniorTipImageUrl();
        this.prescriptionImageUrls = inputProblem.getPrescriptionImageUrls();
        this.answerType = inputProblem.getAnswerType();
        this.isConfirmed = inputProblem.isConfirmed();
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
        return problemAdminId != null
                && practiceTestId != null
                && problemType != null
                && title != null && !title.getTitle().isEmpty()
                && answer != null && !answer.getValue().isEmpty()
                && difficulty != null && difficulty.getDifficulty() != null
                && memo != null && !memo.isEmpty()
                && mainProblemImageUrl != null && !mainProblemImageUrl.isEmpty()
                && mainAnalysisImageUrl != null && !mainAnalysisImageUrl.isEmpty()
                && mainHandwritingExplanationImageUrl != null && !mainHandwritingExplanationImageUrl.isEmpty()
                && readingTipImageUrl != null && !readingTipImageUrl.isEmpty()
                && seniorTipImageUrl != null && !seniorTipImageUrl.isEmpty()
                && prescriptionImageUrls != null && !prescriptionImageUrls.isEmpty()
                && prescriptionImageUrls.stream().allMatch(url -> url != null && !url.isEmpty())
                && answerType != null
                && conceptTagIds != null && !conceptTagIds.isEmpty();
    }

    public String getTitle() {
        return title.getTitle();
    }

    public Integer getDifficulty() {
        return difficulty.getDifficulty();
    }
}