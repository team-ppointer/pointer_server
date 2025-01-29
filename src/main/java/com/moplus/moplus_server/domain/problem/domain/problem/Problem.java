package com.moplus.moplus_server.domain.problem.domain.problem;

import com.moplus.moplus_server.domain.problem.domain.Answer;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTest;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemUpdateRequest;
import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Problem extends BaseEntity {

    @EmbeddedId
    ProblemId id;

    Long practiceTestId;
    int number;
    @Embedded
    Answer answer;
    String comment;
    String mainProblemImageUrl;
    String mainAnalysisImageUrl;
    String readingTipImageUrl;
    String seniorTipImageUrl;
    String prescriptionImageUrl;
    @ElementCollection
    @CollectionTable(name = "problem_concept", joinColumns = @JoinColumn(name = "concept_tag_id"))
    Set<Long> conceptTagIds;
    private ProblemType problemType;
    private boolean isPublished;
    private boolean isModified;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    @OrderBy("sequence ASC")
    private List<ChildProblem> childProblems = new ArrayList<>();

    @Builder
    public Problem(ProblemId id, PracticeTest practiceTest, int number, String answer, String comment,
                   String mainProblemImageUrl,
                   String mainAnalysisImageUrl, String readingTipImageUrl, String seniorTipImageUrl,
                   String prescriptionImageUrl, ProblemType problemType, Set<Long> conceptTagIds,
                   List<ChildProblem> childProblems) {
        this.id = id;
        this.practiceTestId = practiceTest.getId();
        this.number = number;
        this.comment = comment;
        this.mainProblemImageUrl = mainProblemImageUrl;
        this.mainAnalysisImageUrl = mainAnalysisImageUrl;
        this.readingTipImageUrl = readingTipImageUrl;
        this.seniorTipImageUrl = seniorTipImageUrl;
        this.prescriptionImageUrl = prescriptionImageUrl;
        this.problemType = ProblemType.getTypeForProblem(practiceTest.getSubject().getValue(), number);
        this.answer = new Answer(answer, this.problemType);
        this.conceptTagIds = conceptTagIds;
        this.childProblems = childProblems;
        this.isPublished = false;
        this.isModified = false;
    }

    public String getAnswer() {
        return answer.getValue();
    }

    public void addChildProblem(ChildProblemPostRequest request) {
        ChildProblem childProblem = ChildProblem.builder()
                .imageUrl(request.imageUrl())
                .problemType(request.problemType())
                .answer(request.answer())
                .conceptTagIds(request.conceptTagIds())
                .sequence(request.sequence())
                .build();
        childProblems.add(request.sequence(), childProblem);
    }

    public void updateChildProblem(ChildProblemUpdateRequest request) {
        childProblems.get(request.sequence()).update(request);
    }

    public void deleteChildProblem(Long childProblemId) {
        childProblems.forEach(childProblem -> {
            if (childProblem.getId().equals(childProblemId)) {
                childProblems.remove(childProblem);
            }
        });
    }
}
