package com.moplus.moplus_server.domain.problemset.dto.response;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemSetSearchGetResponse {
    @NotNull(message = "ID 값은 필수입니다.")
    private Long id;
    private String problemSetTitle;
    private ProblemSetConfirmStatus confirmStatus;
    @NotNull(message = "컬렉션 값은 필수입니다.")
    private List<ProblemThumbnailResponse> problemThumbnailResponses;

    public ProblemSetSearchGetResponse(
            Long id, String problemSetTitle, ProblemSetConfirmStatus confirmStatus,
            List<ProblemThumbnailResponse> problemThumbnailResponses
    ) {
        this.id = id;
        this.problemSetTitle = problemSetTitle;
        this.confirmStatus = confirmStatus;
        this.problemThumbnailResponses = problemThumbnailResponses;
    }
}
