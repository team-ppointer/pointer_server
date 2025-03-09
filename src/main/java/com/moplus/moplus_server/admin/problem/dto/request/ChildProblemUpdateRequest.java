package com.moplus.moplus_server.admin.problem.dto.request;

import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public record ChildProblemUpdateRequest(
        @NotNull(message = "새끼문제 ID는 필수입니다.")
        Long childProblemId,
        String imageUrl,
        AnswerType answerType,
        String answer,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        Set<Long> conceptTagIds,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        List<String> prescriptionImageUrls
) {
}
