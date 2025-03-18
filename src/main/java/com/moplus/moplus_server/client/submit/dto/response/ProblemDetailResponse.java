package com.moplus.moplus_server.client.submit.dto.response;

import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemDetailResponse(
        String imageUrl,
        List<String> prescriptionImageUrls,
        ProblemSubmitStatus submitStatus
) {
    public static ProblemDetailResponse of(String imageUrl, List<String> prescriptionImageUrls, ProblemSubmitStatus submitStatus) {
        return ProblemDetailResponse.builder()
                .imageUrl(imageUrl)
                .prescriptionImageUrls(prescriptionImageUrls)
                .submitStatus(submitStatus)
                .build();
    }
}
