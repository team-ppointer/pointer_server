package com.moplus.moplus_server.domain.problemset.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemThumbnailResponse {
    private String mainProblemImageUrl;

    public ProblemThumbnailResponse(String mainProblemImageUrl) {
        this.mainProblemImageUrl = mainProblemImageUrl;
    }
}
