package com.moplus.moplus_server.domain.problem.dto.response;

public record PresignedUrlResponse(
        String data
) {
    public static PresignedUrlResponse of(String presignedUrl) {
        return new PresignedUrlResponse(
                presignedUrl
        );
    }
}
