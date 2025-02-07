package com.moplus.moplus_server.domain.problemset.dto.request;

import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import java.util.List;

public record ProblemSetPostRequest(
        String problemSetTitle,
        List<String> problems
) {
    public ProblemSet toEntity(List<ProblemId> problemIdList) {
        return ProblemSet.builder()
                .title(verifyTitle(this.problemSetTitle))
                .problemIds(problemIdList)
                .build();
    }

    // 빈 타이틀 "제목 없음"으로 임시 세팅
    private static String verifyTitle(String title) {
        return (title == null || title.trim().isEmpty()) ? "제목 없음" : title;
    }
}
