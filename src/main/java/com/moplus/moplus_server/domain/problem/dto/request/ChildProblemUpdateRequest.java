package com.moplus.moplus_server.domain.problem.dto.request;

import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import java.util.Set;

public record ChildProblemUpdateRequest(
        Long id,
        String imageUrl,
        AnswerType answerType,
        String answer,
        Set<Long> conceptTagIds
) {
}
