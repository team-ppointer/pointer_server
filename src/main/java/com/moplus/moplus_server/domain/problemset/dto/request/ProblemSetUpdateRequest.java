package com.moplus.moplus_server.domain.problemset.dto.request;

import java.util.List;

public record ProblemSetUpdateRequest(
        String problemSetName,
        List<String> problems
) {
}
