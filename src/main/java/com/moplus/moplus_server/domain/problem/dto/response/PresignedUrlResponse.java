package com.moplus.moplus_server.domain.problem.dto.response;

public record PresignedUrlResponse(
        String presignedUrl
) {
    public static PresignedUrlResponse of(String presignedUrl) {
        return new PresignedUrlResponse(
                presignedUrl
        );
    }
}
