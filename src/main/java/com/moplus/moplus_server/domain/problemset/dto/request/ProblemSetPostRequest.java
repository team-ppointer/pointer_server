package com.moplus.moplus_server.domain.problemset.dto.request;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import java.util.List;

public record ProblemSetPostRequest(
        String problemSetTitle,
        List<Long> problems
) {
    public ProblemSet toEntity(List<Long> problemIdList) {
        return ProblemSet.builder()
                .title(this.problemSetTitle)
                .problemIds(problemIdList)
                .build();
    }
}
