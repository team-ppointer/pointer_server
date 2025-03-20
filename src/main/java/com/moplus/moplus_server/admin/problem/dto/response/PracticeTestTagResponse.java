package com.moplus.moplus_server.admin.problem.dto.response;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import jakarta.validation.constraints.NotNull;

public record PracticeTestTagResponse(
        @NotNull(message = "기출 모의고사 태그 ID는 필수입니다")
        Long id,
        @NotNull(message = "기출 모의고사 태그 이름은 필수입니다")
        String name
) {
    public static PracticeTestTagResponse of(PracticeTestTag practiceTestTag) {
        return new PracticeTestTagResponse(
                practiceTestTag.getId(),
                practiceTestTag.getName()
        );
    }
}
