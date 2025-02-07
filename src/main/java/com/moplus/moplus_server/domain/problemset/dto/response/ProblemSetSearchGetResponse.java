package com.moplus.moplus_server.domain.problemset.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSetSearchGetResponse {
    private String problemSetTitle;
    private List<ProblemThumbnailResponse> problemThumbnailResponses;

    public ProblemSetSearchGetResponse(String problemSetTitle,
                                       List<ProblemThumbnailResponse> problemThumbnailResponses) {
        this.problemSetTitle = problemSetTitle;
        this.problemThumbnailResponses = problemThumbnailResponses;
    }
}
