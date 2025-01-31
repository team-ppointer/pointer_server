package com.moplus.moplus_server.domain.problemset.dto.request;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import java.util.List;

public record ProblemSetPostRequest(
        String problemSetName,
        List<String> problems
) {
    public ProblemSet toEntity(String problemSetName) {
        return ProblemSet.builder()
                .name(problemSetName)
                .build();
    }
}
