package com.moplus.moplus_server.domain.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;

public record PracticeTestTagResponse(
        Long id,
        String name
) {
    public static PracticeTestTagResponse of(PracticeTestTag practiceTestTag) {
        return new PracticeTestTagResponse(
                practiceTestTag.getId(),
                practiceTestTag.getName()
        );
    }
}
