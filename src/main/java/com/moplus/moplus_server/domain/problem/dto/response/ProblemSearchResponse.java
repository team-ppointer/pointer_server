package com.moplus.moplus_server.domain.problem.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ProblemSearchResponse(
        List<ProblemSearchGetResponse> problems,
        long totalCount,
        int currentPage,
        int totalPages
) {
    public static ProblemSearchResponse of(
            List<ProblemSearchGetResponse> problems,
            long totalCount,
            int currentPage,
            int size
    ) {
        return new ProblemSearchResponse(
                problems,
                totalCount,
                currentPage,
                (int) Math.ceil((double) totalCount / size)
        );
    }
} 