package com.moplus.moplus_server.domain.problem.domain.childProblem;

import com.moplus.moplus_server.domain.problem.domain.Answer;
import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.global.common.BaseEntity;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
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
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChildProblem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_problem_id")
    Long id;
    @ElementCollection
    @CollectionTable(name = "child_problem_concept", joinColumns = @JoinColumn(name = "child_problem_id"))
    @Column(name = "concept_tag_id")
    Set<Long> conceptTagIds;
    private String imageUrl;
    @Embedded
    private Answer answer;
    @Enumerated(EnumType.STRING)
    private AnswerType answerType;
    private int sequence;

    @Builder
    public ChildProblem(String imageUrl, AnswerType answerType, String answer, Set<Long> conceptTagIds,
                        int sequence) {
        validateAnswerByType(answer, answerType);
        this.imageUrl = imageUrl;
        this.answerType = answerType;
        this.answer = new Answer(answer, answerType);
        this.conceptTagIds = conceptTagIds;
        this.sequence = sequence;
    }

    public void validateAnswerByType(String answer, AnswerType answerType) {
        if (this.answerType == AnswerType.MULTIPLE_CHOICE) {
            if (!answer.matches("^[1-5]*$")) {
                throw new InvalidValueException(ErrorCode.INVALID_MULTIPLE_CHOICE_ANSWER);
            }
        }
    }

    public void update(ChildProblem input) {
        this.imageUrl = input.imageUrl;
        this.answerType = input.answerType;
        this.answer = input.answer;
        this.conceptTagIds = input.conceptTagIds;
        this.sequence = input.sequence;
    }

    public String getAnswer() {
        return answer.getValue();
    }
}
