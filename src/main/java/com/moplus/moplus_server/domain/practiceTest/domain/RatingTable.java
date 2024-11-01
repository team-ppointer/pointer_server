package com.moplus.moplus_server.domain.practiceTest.domain;

import com.moplus.moplus_server.domain.practiceTest.repository.converter.RatingRowConverter;
import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class RatingTable extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_table_id")
    Long id;

    private Long practiceTestId;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    private String provider;

    @Convert(converter = RatingRowConverter.class)
    private List<RatingRow> ratingRows;

    public RatingTable(Long practiceTestId, Subject subject, String provider, List<RatingRow> ratingRows) {
        this.practiceTestId = practiceTestId;
        this.subject = subject;
        this.provider = provider;
        this.ratingRows = ratingRows;
    }

    public void validateRatingRows() {
        if (ratingRows == null || ratingRows.isEmpty()) {
            throw new IllegalArgumentException("RatingRow 목록이 비어있습니다.");
        }

        final int ratingRowsSize = 9;
        if (ratingRows.size() != ratingRowsSize) {
            throw new IllegalArgumentException("RatingRow 목록은 9개의 등급으로 이루어져야합니다. 현재 size : " + ratingRows.size());
        }
    }

}
