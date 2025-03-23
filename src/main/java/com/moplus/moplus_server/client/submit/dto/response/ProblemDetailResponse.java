package com.moplus.moplus_server.client.submit.dto.response;

import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemDetailResponse(
        String imageUrl,
        List<String> prescriptionImageUrls,
        ProblemSubmitStatus submitStatus
) {
    public static ProblemDetailResponse of(Problem problem, ProblemSubmitStatus submitStatus) {
        return ProblemDetailResponse.builder()
                .imageUrl(problem.getMainProblemImageUrl())
                .prescriptionImageUrls(problem.getPrescriptionImageUrls())
                .submitStatus(submitStatus)
                .build();
    }
}
