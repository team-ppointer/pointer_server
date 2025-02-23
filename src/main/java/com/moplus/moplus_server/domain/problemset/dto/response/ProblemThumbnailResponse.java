package com.moplus.moplus_server.domain.problemset.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemThumbnailResponse {
    private String problemTitle;
    private String problemMemo;
    private String mainProblemImageUrl;

    public ProblemThumbnailResponse(String problemTitle, String problemMemo, String mainProblemImageUrl) {
        this.problemTitle = problemTitle;
        this.problemMemo = problemMemo;
        this.mainProblemImageUrl = mainProblemImageUrl;
    }
}
