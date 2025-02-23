package com.moplus.moplus_server.domain.problemset.dto.request;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProblemSetPostRequest(
        String problemSetTitle,
        @NotNull(message = "컬렉션 값은 필수입니다.")
        List<Long> problems
) {
    public ProblemSet toEntity(List<Long> problemIdList) {
        return ProblemSet.builder()
                .title(this.problemSetTitle)
                .problemIds(problemIdList)
                .build();
    }
}
