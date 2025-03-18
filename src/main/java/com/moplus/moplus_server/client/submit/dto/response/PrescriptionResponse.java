package com.moplus.moplus_server.client.submit.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record PrescriptionResponse(
    List<ChildProblemDetailResponse> childProblem,
    ProblemDetailResponse mainProblem
) {
    public static PrescriptionResponse of(List<ChildProblemDetailResponse> childProblem,
                                          ProblemDetailResponse mainProblem) {
        return PrescriptionResponse.builder()
                .childProblem(childProblem)
                .mainProblem(mainProblem)
                .build();
    }
}
