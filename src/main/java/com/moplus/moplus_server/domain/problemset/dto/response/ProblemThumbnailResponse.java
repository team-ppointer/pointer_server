package com.moplus.moplus_server.domain.problemset.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProblemThumbnailResponse that = (ProblemThumbnailResponse) o;
        return Objects.equals(problemTitle, that.problemTitle) &&
                Objects.equals(problemMemo, that.problemMemo) &&
                Objects.equals(mainProblemImageUrl, that.mainProblemImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(problemTitle, problemMemo, mainProblemImageUrl);
    }
}
