package com.moplus.moplus_server.domain.problemset.dto.request;

import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import java.util.List;

public record ProblemSetPostRequest(
        String problemSetName,
        List<String> problems
) {
    public ProblemSet toEntity(List<ProblemId> problemIdList) {
        return ProblemSet.builder()
                .name(this.problemSetName)
                .problemIds(problemIdList)
                .build();
    }
}
