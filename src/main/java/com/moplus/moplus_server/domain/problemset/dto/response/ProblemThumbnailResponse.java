package com.moplus.moplus_server.domain.problemset.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemThumbnailResponse {
    private String mainProblemImageUrl;
    private List<String> tagNames;

    public ProblemThumbnailResponse(String mainProblemImageUrl, List<String> tagNames) {
        this.mainProblemImageUrl = mainProblemImageUrl;
        this.tagNames = tagNames;
    }
}
