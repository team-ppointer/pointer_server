package com.moplus.moplus_server.domain.problem.dto.response;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSearchGetResponse {
    private String problemId;
    private String comment;
    private String mainProblemImageUrl;
    private Set<ConceptTagSearchResponse> conceptTagResponses;

    public ProblemSearchGetResponse(String problemId, String comment, String mainProblemImageUrl,
                                    Set<ConceptTagSearchResponse> conceptTagResponses) {
        this.problemId = problemId;
        this.comment = comment;
        this.mainProblemImageUrl = mainProblemImageUrl;
        this.conceptTagResponses = conceptTagResponses;
    }
}


