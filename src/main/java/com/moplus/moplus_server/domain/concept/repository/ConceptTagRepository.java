package com.moplus.moplus_server.domain.concept.repository;

import com.moplus.moplus_server.domain.concept.domain.ConceptTag;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptTagRepository extends JpaRepository<ConceptTag, Long> {

    default void existsByIdElseThrow(Set<Long> ids) {
        List<Long> foundIds = findAllById(ids).stream()
                .map(ConceptTag::getId) // 엔티티의 ID 추출
                .toList();

        if (ids.size() != foundIds.size()) {
            throw new NotFoundException(ErrorCode.CONCEPT_TAG_NOT_FOUND_IN_LIST);
        }
    }


    default List<ConceptTag> findAllByIdsElseThrow(Set<Long> ids) {
        List<ConceptTag> conceptTags = findAllById(ids);
        if (conceptTags.size() != ids.size()) {
            throw new NotFoundException(ErrorCode.CONCEPT_TAG_NOT_FOUND_IN_LIST);
        }
        return conceptTags;
    }
}
