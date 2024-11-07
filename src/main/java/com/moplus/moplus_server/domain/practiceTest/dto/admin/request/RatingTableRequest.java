package com.moplus.moplus_server.domain.practiceTest.dto.admin.request;

import com.moplus.moplus_server.domain.practiceTest.domain.RatingRow;
import com.moplus.moplus_server.domain.practiceTest.domain.RatingTable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RatingTableRequest {
    private Long id;
    private Long practiceId;
    private String ratingProvider;
    private List<RatingRow> ratingRows;

    @Builder
    public RatingTableRequest(Long id, Long practiceId, String ratingProvider, List<RatingRow> ratingRows) {
        this.id = id;
        this.practiceId = practiceId;
        this.ratingProvider = ratingProvider;
        this.ratingRows = ratingRows;
    }

    public RatingTableRequest(String ratingProvider) {
        this.ratingProvider = ratingProvider;
        this.ratingRows = List.of(new RatingRow(1), new RatingRow(2), new RatingRow(3), new RatingRow(4), new RatingRow(5),
                new RatingRow(6), new RatingRow(7), new RatingRow(8), new RatingRow(9));

    }

    public static List<RatingTableRequest> getDefaultRatingTableRequest() {
        return List.of(new RatingTableRequest("대성마이맥"), new RatingTableRequest("이투스"), new RatingTableRequest("메가스터디"));
    }

    public static RatingTableRequest getRatingTableRequest(RatingTable ratingTable) {
        return RatingTableRequest.builder()
                .id(ratingTable.getId())
                .practiceId(ratingTable.getPracticeTestId())
                .ratingProvider(ratingTable.getRatingProvider())
                .ratingRows(ratingTable.getRatingRows())
                .build();
    }

    public RatingTable toEntity() {
        return RatingTable.builder()
                .practiceTestId(this.practiceId)
                .ratingProvider(this.ratingProvider)
                .ratingRows(this.ratingRows)
                .build();
    }
}
