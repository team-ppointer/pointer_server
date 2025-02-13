package com.moplus.moplus_server.domain.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import java.util.Set;
import lombok.Builder;

@Builder
public record ChildProblemGetResponse(
        Long childProblemId,
        String imageUrl,
        AnswerType answerType,
        String answer,
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
