package com.moplus.moplus_server.domain.problem.domain.problem;

import com.moplus.moplus_server.domain.problem.domain.Answer;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.repository.converter.StringListConverter;
import com.moplus.moplus_server.global.common.BaseEntity;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
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
import jakarta.persistence.OrderColumn;
import java.util.ArrayList;
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
    ProblemCustomId problemCustomId;
    Long practiceTestId;
    Integer number;
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
    @OrderColumn(name = "sequence")
    private List<ChildProblem> childProblems = new ArrayList<>();

    @Embedded
    private RecommendedTime recommendedTime;

    @Builder
    public Problem(List<ChildProblem> childProblems, boolean isConfirmed, AnswerType answerType,
                   Set<Long> conceptTagIds, Integer difficulty, String mainHandwritingExplanationImageUrl,
                   List<String> prescriptionImageUrls, String seniorTipImageUrl, String readingTipImageUrl,
                   String mainAnalysisImageUrl, String mainProblemImageUrl, String memo, String answer, String title,
                   ProblemType problemType, int number, PracticeTestTag practiceTestTag,
                   ProblemCustomId problemCustomId, Integer recommendedMinute, Integer recommendedSecond) {
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
        this.practiceTestId = practiceTestTag != null ? practiceTestTag.getId() : null;
        this.problemCustomId = problemCustomId;
        this.recommendedTime = new RecommendedTime(recommendedMinute, recommendedSecond);
    }

    public String getAnswer() {
        return this.answer != null ? answer.getValue() : null;
    }

    public void update(Problem inputProblem) {
        this.problemCustomId = new ProblemCustomId(inputProblem.getProblemCustomId());
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
        this.mainHandwritingExplanationImageUrl = inputProblem.getMainHandwritingExplanationImageUrl();
        this.readingTipImageUrl = inputProblem.getReadingTipImageUrl();
        this.seniorTipImageUrl = inputProblem.getSeniorTipImageUrl();
        this.prescriptionImageUrls = inputProblem.getPrescriptionImageUrls();
        this.answerType = inputProblem.getAnswerType();
        this.recommendedTime = new RecommendedTime(
                inputProblem.getRecommendedTime().getMinute(),
                inputProblem.getRecommendedTime().getSecond()
        );
    }

    public void updateChildProblem(List<ChildProblem> inputChildProblems) {
        if (this.childProblems.size() != inputChildProblems.size()) {
            throw new InvalidValueException(ErrorCode.INVALID_CHILD_PROBLEM_SIZE);
        }

        for (int i = 0; i < inputChildProblems.size(); i++) {
            this.childProblems.get(i).update(inputChildProblems.get(i));
        }
    }

    public void addChildProblem(ChildProblem childProblem) {
        if (this.isConfirmed) {
            throw new InvalidValueException(ErrorCode.INVALID_CHILD_PROBLEM_SIZE);
        }
        this.childProblems.add(childProblem);
    }

    public void deleteChildProblem(Long childProblemId) {
        if (this.isConfirmed) {
            throw new InvalidValueException(ErrorCode.INVALID_CHILD_PROBLEM_SIZE);
        }
        this.childProblems.removeIf(childProblem -> childProblem.getId().equals(childProblemId));
    }

    public boolean isValid() {
        return problemCustomId != null
                && problemType != null
                && title != null && !title.getTitle().isEmpty()
                && answer != null && !answer.getValue().isEmpty()
                && difficulty != null && difficulty.getDifficulty() != null
                && mainProblemImageUrl != null && !mainProblemImageUrl.isEmpty()
                && mainAnalysisImageUrl != null && !mainAnalysisImageUrl.isEmpty()
                && mainHandwritingExplanationImageUrl != null && !mainHandwritingExplanationImageUrl.isEmpty()
                && readingTipImageUrl != null && !readingTipImageUrl.isEmpty()
                && seniorTipImageUrl != null && !seniorTipImageUrl.isEmpty()
                && prescriptionImageUrls != null && !prescriptionImageUrls.isEmpty()
                && prescriptionImageUrls.stream().allMatch(url -> url != null && !url.isEmpty())
                && answerType != null
                && conceptTagIds != null && !conceptTagIds.isEmpty()
                && recommendedTime != null
                && recommendedTime.getMinute() != null && recommendedTime.getMinute() >= 0
                && recommendedTime.getSecond() != null && recommendedTime.getSecond() >= 0;
    }

    public String getTitle() {
        return title != null ? title.getTitle() : "제목 없음";
    }

    public Integer getDifficulty() {
        return difficulty != null ? difficulty.getDifficulty() : null;
    }

    public String getProblemCustomId() {
        return problemCustomId.getId();
    }

    public Integer getNumber() {
        return number != null ? number : null;
    }

}