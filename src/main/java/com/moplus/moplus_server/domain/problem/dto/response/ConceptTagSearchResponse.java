package com.moplus.moplus_server.domain.problem.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConceptTagSearchResponse {
    @NotNull(message = "개념 태그 ID는 필수입니다")
    private Long id;
    @NotNull(message = "개념 태그 이름은 필수입니다")
    private String name;

    public ConceptTagSearchResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
