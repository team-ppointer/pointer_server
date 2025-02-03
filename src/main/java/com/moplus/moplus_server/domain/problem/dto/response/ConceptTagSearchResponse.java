package com.moplus.moplus_server.domain.problem.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConceptTagSearchResponse {
    private Long id;
    private String name; // 예시로 태그 이름을 추가 (필요에 따라 변경 가능)

    public ConceptTagSearchResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
