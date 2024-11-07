package com.moplus.moplus_server.domain.practiceTest.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RatingRow {

    private int rating;
    private String rawScores;
    private Integer standardScores;
    private Integer percentiles;

    public RatingRow(int index){
        this.rating = index;
    }

    public RatingRow(int rating, String rawScores, int standardScores, int percentiles) {
        validate(rating, rawScores, standardScores, percentiles);
        this.rating = rating;
        this.rawScores = rawScores;
        this.standardScores = standardScores;
        this.percentiles = percentiles;
    }

    public void validate(int rating, String rawScores, Integer standardScores, Integer percentiles) {
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
