package com.moplus.moplus_server.domain.problem.dto.request;

import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import java.util.Set;

public record ChildProblemPostRequest(
        String imageUrl,
        AnswerType answerType,
        String answer,
        Set<Long> conceptTagIds,
        int sequence
) {
}
