package com.moplus.moplus_server.domain.concept.dto.response;

import com.moplus.moplus_server.domain.concept.domain.ConceptTag;
import jakarta.validation.constraints.NotNull;

public record ConceptTagResponse(
        @NotNull(message = "개념 태그 ID는 필수입니다")
        Long id,
        @NotNull(message = "개념 태그 이름은 필수입니다")
        String name
) {
    public static ConceptTagResponse of(ConceptTag entity) {
        return new ConceptTagResponse(
                entity.getId(),
                entity.getName()
        );
    }
}
