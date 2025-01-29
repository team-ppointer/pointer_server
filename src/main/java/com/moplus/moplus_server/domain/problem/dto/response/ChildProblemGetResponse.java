package com.moplus.moplus_server.domain.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import java.util.Set;
import lombok.Builder;

@Builder
public record ChildProblemGetResponse(
        Long childProblemId,
        String imageUrl,
        ProblemType problemType,
        String answer,
        Set<Long> conceptTagIds
) {

    public static ChildProblemGetResponse of(ChildProblem childProblem) {
        return ChildProblemGetResponse.builder()
                .childProblemId(childProblem.getId())
                .imageUrl(childProblem.getImageUrl())
                .problemType(childProblem.getProblemType())
                .answer(childProblem.getAnswer())
                .conceptTagIds(childProblem.getConceptTagIds())
                .build();
    }
}
