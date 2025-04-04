package com.moplus.moplus_server.admin.problemset.dto.response;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSummaryResponse{
    @NotNull(message = "문항 ID는 필수입니다")
    private Long problemId;
    @NotNull(message = "문항 custom ID는 필수입니다")
    private String problemCustomId;
    private String problemTitle;
    private String memo;
    private String mainProblemImageUrl;
    @NotNull(message = "컬렉션 값은 필수입니다.")
    private Set<String> tagNames;
    public  ProblemSummaryResponse (Problem problem, Set<String> tagNames) {
        this.problemId = problem.getId();
        this.problemCustomId = problem.getProblemCustomId();
        this.problemTitle = problem.getTitle();
        this.memo = problem.getMemo();
        this.mainProblemImageUrl = getMainProblemImageUrl();
        this.tagNames = tagNames;
    }
}
