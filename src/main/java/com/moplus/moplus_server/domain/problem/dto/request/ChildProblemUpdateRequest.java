package com.moplus.moplus_server.domain.problem.dto.request;

import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public record ChildProblemUpdateRequest(
        @Schema(description = "새로 생성되는 새끼문항은 빈 값입니다.")
        Long id,
        String imageUrl,
        ProblemType problemType,
        String answer,
        Set<Long> conceptTagIds,
        int sequence
) {
}
