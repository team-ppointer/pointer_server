package com.moplus.moplus_server.domain.v0.practiceTest.domain;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.Subject;
import com.moplus.moplus_server.domain.v0.practiceTest.dto.admin.request.RatingTableRequest;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.converter.RatingRowConverter;
import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.util.List;
import lombok.Builder;
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

    private String ratingProvider;

    @Lob
    @Convert(converter = RatingRowConverter.class)
    private List<RatingRow> ratingRows;

    @Builder
    public RatingTable(Long practiceTestId, Subject subject, String ratingProvider, List<RatingRow> ratingRows) {
        this.practiceTestId = practiceTestId;
        this.subject = subject;
        this.ratingProvider = ratingProvider;
        this.ratingRows = ratingRows;
    }

    public void updateByRatingTableRequest(RatingTableRequest request) {
        this.ratingProvider = request.getRatingProvider();
        this.ratingRows = request.getRatingRows();
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
