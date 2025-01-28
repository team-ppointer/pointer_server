package com.moplus.moplus_server.domain.v0.TestResult.dto.response;

import com.moplus.moplus_server.domain.v0.TestResult.entity.EstimatedRating;

public record EstimatedRatingGetResponse(
        String ratingProvider,
        int estimatedRating
) {

    public static EstimatedRatingGetResponse from(EstimatedRating entity) {
        return new EstimatedRatingGetResponse(
                entity.getRatingProvider(),
                entity.getEstimatedRating()
        );
    }
}
