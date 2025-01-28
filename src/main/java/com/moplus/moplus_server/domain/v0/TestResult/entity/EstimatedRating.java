package com.moplus.moplus_server.domain.v0.TestResult.entity;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.RatingRow;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.RatingTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class EstimatedRating {

    private static final int RATING_RANGE = 9;
    private static final int MIN_RATING = 9;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimated_rating_id")
    private Long id;
    private Long testResultId;
    private Integer estimatedRating;
    private String ratingProvider;


    public EstimatedRating(int estimatedRating, Long testResultId, String ratingProvider) {
        this.estimatedRating = estimatedRating;
        this.testResultId = testResultId;
        this.ratingProvider = ratingProvider;
    }

    public static EstimatedRating of(int testScore, Long testResultId, RatingTable ratingTables) {
        int estimatedRating = calculateEstimatedRating(testScore, ratingTables.getRatingRows());
        return new EstimatedRating(estimatedRating, testResultId, ratingTables.getRatingProvider());
    }

    private static int calculateEstimatedRating(int testScore, List<RatingRow> ratingRows) {

        for (int i = 0; i < RATING_RANGE - 1; i++) {
            RatingRow ratingRow = ratingRows.get(i);
            String rawScores = ratingRow.getRawScores();

            int[] scoreRange = parseRawScores(rawScores);
            int min = scoreRange[0];
            int max = scoreRange[1];
            if (testScore >= min) {
                return i + 1;
            }
        }
        return MIN_RATING;
    }

    private static int[] parseRawScores(String rawScores) {
        String[] parts = rawScores.split("[^0-9]+");

        if (parts.length == 2) {
            int first = Integer.parseInt(parts[0]);
            int second = Integer.parseInt(parts[1]);

            int min = Math.min(first, second);
            int max = Math.max(first, second);

            return new int[]{min, max};
        } else if (parts.length == 1) {
            int min = Integer.parseInt(parts[0]);
            return new int[]{min, min};
        } else {
            throw new IllegalArgumentException("올바른 형식이 아닙니다: " + rawScores);
        }
    }

    public int getEstimatedRating() {
        return estimatedRating;
    }
}
