package com.moplus.moplus_server.domain.practiceTest.dto.admin.request;

import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.domain.RatingRow;
import com.moplus.moplus_server.domain.practiceTest.domain.Subject;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PracticeTestRequest {
    private Long id;
    private String name;
    private String round;
    private String provider;
    private String publicationYear;
    private String subject;
    private List<RatingRow> ratingRows;

    public PracticeTestRequest(Long id, String name, String round, String provider, String publicationYear,
                               String subject,
                               List<RatingRow> ratingRows) {
        this.id = id;
        this.name = name;
        this.round = round;
        this.provider = provider;
        this.publicationYear = publicationYear;
        this.subject = subject;
        this.ratingRows = ratingRows;
    }

    public static PracticeTestRequest getUpdateModelObject(PracticeTest practiceTest, List<RatingRow> ratingRows) {
        return new PracticeTestRequest(
                practiceTest.getId(), practiceTest.getName(), practiceTest.getRound(),
                practiceTest.getProvider(), practiceTest.getPublicationYear(),
                practiceTest.getSubject().getValue(),
                ratingRows
        );
    }

    public static PracticeTestRequest getUpdateModelObject(PracticeTest practiceTest) {
        return new PracticeTestRequest(
                practiceTest.getId(), practiceTest.getName(), practiceTest.getRound(),
                practiceTest.getProvider(), practiceTest.getPublicationYear(),
                practiceTest.getSubject().getValue(),
                List.of(new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow())
        );
    }

    public static PracticeTestRequest getCreateModelObject() {
        return new PracticeTestRequest(
                null, "", "", "", "", null,
                List.of(new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow(), new RatingRow())
                );
    }

    public PracticeTest toEntity() {
        return PracticeTest.builder()
                .name(this.name)
                .provider(this.provider)
                .round(this.round)
                .publicationYear(this.publicationYear)
                .subject(Subject.fromValue(this.subject))
                .build();
    }
}
