package com.moplus.moplus_server.domain.concept.dto.response;

import com.moplus.moplus_server.domain.concept.domain.ConceptTag;

public record ConceptTagResponse(
        Long id,
        String name
) {
    public static ConceptTagResponse of(ConceptTag entity) {
        return new ConceptTagResponse(
                entity.getId(),
                entity.getName()
        );
    }
}
