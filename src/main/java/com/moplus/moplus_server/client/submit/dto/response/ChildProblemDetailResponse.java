package com.moplus.moplus_server.client.submit.dto.response;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import java.util.List;
import lombok.Builder;

@Builder
public record ChildProblemDetailResponse(
        String imageUrl,
        List<String> prescriptionImageUrls,
        ChildProblemSubmitStatus submitStatus
) {
    public static ChildProblemDetailResponse of(String imageUrl, List<String> prescriptionImageUrls, ChildProblemSubmitStatus submitStatus) {
        return ChildProblemDetailResponse.builder()
                .imageUrl(imageUrl)
                .prescriptionImageUrls(prescriptionImageUrls)
                .submitStatus(submitStatus)
                .build();
    }
}
