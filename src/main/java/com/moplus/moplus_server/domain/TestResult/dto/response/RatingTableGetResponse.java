package com.moplus.moplus_server.domain.TestResult.dto.response;

import com.moplus.moplus_server.domain.practiceTest.domain.RatingRow;
import com.moplus.moplus_server.domain.practiceTest.domain.RatingTable;
import java.util.List;
import lombok.Builder;

@Builder
public record RatingTableGetResponse(
        Long id,
        Long practiceId,
        String ratingProvider,
        List<RatingRow>ratingRows
) {
    public static RatingTableGetResponse from(RatingTable ratingTable){
        return RatingTableGetResponse.builder()
                .id(ratingTable.getId())
                .practiceId(ratingTable.getPracticeTestId())
                .ratingProvider(ratingTable.getRatingProvider())
                .ratingRows(ratingTable.getRatingRows())
                .build();
    }
}
