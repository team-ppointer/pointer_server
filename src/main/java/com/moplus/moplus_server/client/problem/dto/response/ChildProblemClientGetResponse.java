package com.moplus.moplus_server.client.problem.dto.response;

import com.moplus.moplus_server.client.submit.domain.ChildProblemSubmitStatus;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ChildProblemClientGetResponse(
        @NotNull(message = "문항번호는 필수입니다.")
        int problemNumber,
        @NotNull(message = "새끼문항번호는 필수입니다.")
        int childProblemNumber,
        @NotNull(message = "이미지URL은 필수입니다.")
        String imageUrl,
        @NotNull(message = "새끼문항 제출상태는 필수입니다.")
        ChildProblemSubmitStatus status,
        @NotNull(message = "답변타입은 필수입니다.")
        AnswerType answerType,
        @NotNull(message = "정답은 필수입니다.")
        String answer
) {
    public static ChildProblemClientGetResponse of(int problemNumber, int childProblemNumber, ChildProblem childProblem,
                                                   ChildProblemSubmitStatus status
    ) {
        return ChildProblemClientGetResponse.builder()
                .problemNumber(problemNumber)
                .childProblemNumber(childProblemNumber)
                .imageUrl(childProblem.getImageUrl())
                .status(status)
                .answerType(childProblem.getAnswerType())
                .answer(status == ChildProblemSubmitStatus.CORRECT || status == ChildProblemSubmitStatus.RETRY_CORRECT
                        ? childProblem.getAnswer() : null)
                .build();
    }
}
