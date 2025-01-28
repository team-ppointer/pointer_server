package com.moplus.moplus_server.domain.v0.TestResult.dto.response;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.RatingRow;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.RatingTable;
import java.util.List;
import lombok.Builder;

@Builder
public record RatingTableGetResponse(
        Long id,
        Long practiceId,
        String ratingProvider,
        List<RatingRow> ratingRows
) {
    public static RatingTableGetResponse from(RatingTable ratingTable) {
        return RatingTableGetResponse.builder()
                .id(ratingTable.getId())
                .practiceId(ratingTable.getPracticeTestId())
                .ratingProvider(ratingTable.getRatingProvider())
                .ratingRows(ratingTable.getRatingRows())
                .build();
    }
}
