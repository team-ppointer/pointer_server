package com.moplus.moplus_server.domain.problem.dto.request;

import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import java.util.Set;

public record ChildProblemUpdateRequest(
        Long id,
        String imageUrl,
        ProblemType problemType,
        String answer,
        Set<Long> conceptTagIds,
        int sequence
) {
}
