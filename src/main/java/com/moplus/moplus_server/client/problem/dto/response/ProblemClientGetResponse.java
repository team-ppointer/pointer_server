package com.moplus.moplus_server.client.problem.dto.response;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record ProblemClientGetResponse(
        @NotNull(message = "문항번호는 필수입니다.")
        int number,
        @NotNull(message = "문항이미지는 필수입니다.")
        String imageUrl,
        @NotNull(message = "추천시간은 필수입니다.")
        Integer recommendedMinute,
        @NotNull(message = "추천시간은 필수입니다.")
        Integer recommendedSecond,
        @NotNull(message = "문항제출 상태는 필수입니다.")
        ProblemSubmitStatus status,
        @NotNull(message = "새끼문항제출 상태는 필수입니다.")
        List<ChildProblemSubmitStatus> childProblemStatuses,
        @NotNull(message = "답변타입은 필수입니다.")
        AnswerType answerType
) {
    public static ProblemClientGetResponse of(Problem problem, ProblemSubmitStatus status,
                                              List<ChildProblemSubmitStatus> childProblemStatuses, int number) {
        return ProblemClientGetResponse.builder()
                .number(number)
                .imageUrl(problem.getMainProblemImageUrl())
                .status(status)
                .childProblemStatuses(childProblemStatuses)
                .recommendedMinute(problem.getRecommendedTime().getMinute())
                .recommendedSecond(problem.getRecommendedTime().getSecond())
                .answerType(problem.getAnswerType())
                .build();
    }
}
