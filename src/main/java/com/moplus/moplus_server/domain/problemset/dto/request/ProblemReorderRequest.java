package com.moplus.moplus_server.domain.problemset.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProblemReorderRequest(
        @NotNull(message = "컬렉션 값은 필수입니다.")
        List<Long> newProblems
) {
}
