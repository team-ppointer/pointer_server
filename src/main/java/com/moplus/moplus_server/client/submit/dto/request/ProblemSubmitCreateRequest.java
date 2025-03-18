package com.moplus.moplus_server.client.submit.dto.request;

import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import jakarta.validation.constraints.NotNull;

public record ProblemSubmitCreateRequest (
        @NotNull(message = "발행 ID는 필수입니다.")
        Long publishId,
        @NotNull(message = "문항 ID는 필수입니다.")
        Long problemId
){
        public ProblemSubmit toEntity(Long memberId) {
                return ProblemSubmit.builder()
                        .memberId(memberId)
                        .publishId(this.publishId)
                        .problemId(this.problemId)
                        .status(ProblemSubmitStatus.IN_PROGRESS)
                        .build();
        }
}
