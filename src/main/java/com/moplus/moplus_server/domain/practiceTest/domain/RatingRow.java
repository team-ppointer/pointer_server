package com.moplus.moplus_server.domain.practiceTest.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RatingRow {

    private String rating;
    private String rawScores;
    private Integer standardScores;
    private Integer percentiles;

    public RatingRow(String rating, String rawScores, int standardScores, int percentiles) {
        validate(rating, rawScores, standardScores, percentiles);
        this.rating = rating;
        this.rawScores = rawScores;
        this.standardScores = standardScores;
        this.percentiles = percentiles;
    }

    public void validate(String rating, String rawScores, Integer standardScores, Integer percentiles) {
        if (rating == null) {
            throw new IllegalArgumentException("등급 칸이 비어있습니다.");
        }
        if (rawScores == null) {
            throw new IllegalArgumentException("원점수 칸이 비어있습니다.");
        }
        if (standardScores == null) {
            throw new IllegalArgumentException("표준점수 칸이 비어있습니다.");
        }
        if (percentiles == null) {
            throw new IllegalArgumentException("백분위 칸이 비어있습니다.");
        }
        if (standardScores < 0) {
            throw new IllegalArgumentException("표준점수가 음수입니다.");
        }
        if (percentiles < 0) {
            throw new IllegalArgumentException("백분위가 음수입니다.");
        }
    }
}
