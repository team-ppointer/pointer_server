package com.moplus.moplus_server.admin.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Builder;

@Builder
public record ChildProblemGetResponse(
        @NotNull(message = "새끼 문항 ID는 필수입니다")
        Long childProblemId,
        String imageUrl,
        AnswerType answerType,
        String answer,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        Set<Long> conceptTagIds
) {

    public static ChildProblemGetResponse of(ChildProblem childProblem) {
        return ChildProblemGetResponse.builder()
                .childProblemId(childProblem.getId())
                .imageUrl(childProblem.getImageUrl())
                .answerType(childProblem.getAnswerType())
                .answer(childProblem.getAnswer())
                .conceptTagIds(childProblem.getConceptTagIds())
                .build();
    }
}
