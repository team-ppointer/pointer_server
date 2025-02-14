package com.moplus.moplus_server.domain.problem.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSearchGetResponse {
    @NotNull(message = "문항 ID는 필수입니다")
    private String problemId;
    private String memo;
    private String mainProblemImageUrl;
    private Set<ConceptTagSearchResponse> conceptTagResponses;

    public ProblemSearchGetResponse(String problemId, String memo, String mainProblemImageUrl,
                                    Set<ConceptTagSearchResponse> conceptTagResponses) {
        this.problemId = problemId;
        this.memo = memo;
        this.mainProblemImageUrl = mainProblemImageUrl;
        this.conceptTagResponses = conceptTagResponses;
    }
}


