package com.moplus.moplus_server.admin.problem.dto.response;

import jakarta.validation.constraints.NotNull;

public record PresignedUrlResponse(
        @NotNull(message = "사전 서명된 URL은 필수입니다")
        String presignedUrl
) {
    public static PresignedUrlResponse of(String presignedUrl) {
        return new PresignedUrlResponse(
                presignedUrl
        );
    }
}
