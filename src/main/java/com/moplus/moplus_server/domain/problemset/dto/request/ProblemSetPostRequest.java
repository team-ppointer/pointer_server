package com.moplus.moplus_server.domain.problemset.dto.request;

import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import java.util.List;

public record ProblemSetPostRequest(
        String problemSetTitle,
        List<String> problems
) {
    public ProblemSet toEntity(List<ProblemAdminId> problemAdminIdList) {
        return ProblemSet.builder()
                .title(this.problemSetTitle)
                .problemAdminIds(problemAdminIdList)
                .build();
    }
}
