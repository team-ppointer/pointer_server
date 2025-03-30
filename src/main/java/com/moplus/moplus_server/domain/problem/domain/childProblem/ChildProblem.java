package com.moplus.moplus_server.domain.problem.domain.childProblem;

import com.moplus.moplus_server.domain.problem.domain.Answer;
import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.repository.converter.StringListConverter;
import com.moplus.moplus_server.global.common.BaseEntity;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SoftDelete;

@Getter
@Entity
@SoftDelete
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChildProblem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_problem_id")
    Long id;
    @BatchSize(size = 100)
    @ElementCollection
    @CollectionTable(name = "child_problem_concept", joinColumns = @JoinColumn(name = "child_problem_id"))
    @Column(name = "concept_tag_id")
    Set<Long> conceptTagIds;
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    List<String> prescriptionImageUrls;
    private String imageUrl;
    @Embedded
    private Answer answer;
    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    @Builder
    public ChildProblem(Long id, String imageUrl, AnswerType answerType, String answer, Set<Long> conceptTagIds,
                        List<String> prescriptionImageUrls) {
        this.id = id;
        validateAnswerByType(answer, answerType);
        this.imageUrl = imageUrl;
        this.answerType = answerType;
        this.answer = new Answer(answer, answerType);
        this.conceptTagIds = conceptTagIds;
        this.prescriptionImageUrls = prescriptionImageUrls;
    }

    public static ChildProblem createEmptyChildProblem() {
        return ChildProblem.builder()
                .imageUrl("")
                .answerType(AnswerType.SHORT_ANSWER)
                .answer("")
                .conceptTagIds(Set.of())
                .prescriptionImageUrls(List.of())
                .build();
    }

    public void validateAnswerByType(String answer, AnswerType answerType) {
        if (answerType == AnswerType.MULTIPLE_CHOICE) {
            if (!answer.matches("^[1-5]*$")) {
                throw new InvalidValueException(ErrorCode.INVALID_MULTIPLE_CHOICE_ANSWER);
            }
        }
    }

    public void update(ChildProblem input) {
        if (!this.id.equals(input.id)) {
            throw new InvalidValueException(ErrorCode.INVALID_CHILD_PROBLEM_SEQUENCE);
        }
        this.imageUrl = input.imageUrl;
        this.answerType = input.answerType;
        this.answer = input.answer;
        this.conceptTagIds = input.conceptTagIds;
        this.prescriptionImageUrls = input.prescriptionImageUrls;
    }

    public String getAnswer() {
        return answer.getValue();
    }

    public boolean isValid() {
        return imageUrl != null && !imageUrl.isEmpty()
                && answer != null && !answer.getValue().isEmpty()
                && answerType != null
                && conceptTagIds != null && !conceptTagIds.isEmpty()
                && prescriptionImageUrls != null && !prescriptionImageUrls.isEmpty()
                && prescriptionImageUrls.stream().allMatch(url -> url != null && !url.isEmpty());
    }
}
