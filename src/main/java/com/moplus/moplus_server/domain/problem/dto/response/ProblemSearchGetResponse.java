package com.moplus.moplus_server.domain.problem.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSearchGetResponse {
    @NotNull(message = "문항 ID는 필수입니다")
    private Long id;
    @NotNull(message = "문항 custom ID는 필수입니다")
    private String problemCustomId;
    private String title;
    private String memo;
    private String mainProblemImageUrl;
    @NotNull(message = "개념 태그리스트는 필수입니다")
    private Set<ConceptTagSearchResponse> conceptTagResponses;

    public ProblemSearchGetResponse(Long id, String problemCustomId, String title, String memo,
                                    String mainProblemImageUrl,
                                    Set<ConceptTagSearchResponse> conceptTagResponses) {
        this.id = id;
        this.problemCustomId = problemCustomId;
        this.title = title;
        this.memo = memo;
        this.mainProblemImageUrl = mainProblemImageUrl;
        this.conceptTagResponses = conceptTagResponses;
    }
}


