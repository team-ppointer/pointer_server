package com.moplus.moplus_server.domain.problemset.dto.response;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSetSearchGetResponse {
    private String problemSetTitle;
    private ProblemSetConfirmStatus confirmStatus;
    private LocalDate publishedDate;
    private List<ProblemThumbnailResponse> problemThumbnailResponses;

    public ProblemSetSearchGetResponse(
            String problemSetTitle, ProblemSetConfirmStatus confirmStatus, LocalDate publishedDate, List<ProblemThumbnailResponse> problemThumbnailResponses
    ) {
        this.problemSetTitle = problemSetTitle;
        this.confirmStatus = confirmStatus;
        this.publishedDate = publishedDate;
        this.problemThumbnailResponses = problemThumbnailResponses;
    }
}
