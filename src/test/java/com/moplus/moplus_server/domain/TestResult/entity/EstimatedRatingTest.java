package com.moplus.moplus_server.domain.TestResult.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.moplus.moplus_server.domain.v0.TestResult.entity.EstimatedRating;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.RatingRow;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.RatingTable;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EstimatedRatingTest {

    @ParameterizedTest
    @CsvSource({
            "76, 2, 이투스",
            "83, 1, 이투스",
            "73, 2, 이투스",
            "60, 3, 이투스",
            "45, 4, 이투스",
            "28, 5, 이투스",
            "13, 6, 이투스",
            "7, 7, 이투스",
            "6, 8, 이투스",
            "100, 1, 이투스"  // 최고점에 해당하는 테스트 케이스
    })
    void 등급계산_성공(int testScore, int expectedRating, String expectedProvider) {
        // given
        Long testResultId = 1L;
        List<RatingRow> ratingRows = new ArrayList<>();
        ratingRows.add(new RatingRow(1, "83 ~ 84", 136, 96));
        ratingRows.add(new RatingRow(2, "72 ~ 73", 127, 89));
        ratingRows.add(new RatingRow(3, "60 ~ 62", 118, 78));
        ratingRows.add(new RatingRow(4, "45 ~ 47", 106, 60));
        ratingRows.add(new RatingRow(5, "28 ~ 29", 92, 40));
        ratingRows.add(new RatingRow(6, "13 ~ 14", 80, 24));
        ratingRows.add(new RatingRow(7, "7 ~ 8", 75, 8));
        ratingRows.add(new RatingRow(8, "6", 74, 4));
        ratingRows.add(new RatingRow(9, "0", 0, 0));

        RatingTable ratingTable = RatingTable.builder()
                .ratingRows(ratingRows)
                .ratingProvider(expectedProvider)
                .build();

        // when
        EstimatedRating estimatedRating = EstimatedRating.of(testScore, testResultId, ratingTable);

        // then
        assertEquals(expectedRating, estimatedRating.getEstimatedRating());
        assertEquals(expectedProvider, estimatedRating.getRatingProvider());
    }
}