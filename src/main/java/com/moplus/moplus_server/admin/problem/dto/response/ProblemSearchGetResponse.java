package com.moplus.moplus_server.admin.problem.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSearchGetResponse {
    @NotNull(message = "문항 ID는 필수입니다")
    private Long problemId;
    @NotNull(message = "문항 custom ID는 필수입니다")
    private String problemCustomId;
    private String problemTitle;
    private String memo;
    private String mainProblemImageUrl;
    @NotNull(message = "개념 태그리스트는 필수입니다")
    private Set<String> tagNames;

    public ProblemSearchGetResponse(Long problemId, String problemCustomId, String problemTitle, String memo,
                                    String mainProblemImageUrl,
                                    Set<ConceptTagSearchResponse> tagNames) {
        this.problemId = problemId;
        this.problemCustomId = problemCustomId;
        this.problemTitle = problemTitle;
        this.memo = memo;
        this.mainProblemImageUrl = mainProblemImageUrl;
        this.tagNames = tagNames.stream()
                .map(ConceptTagSearchResponse::getName)
                .collect(Collectors.toSet());
    }
}


