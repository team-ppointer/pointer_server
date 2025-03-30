package com.moplus.moplus_server.domain.publish.repository;

import com.moplus.moplus_server.admin.publish.domain.Publish;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishRepository extends JpaRepository<Publish, Long> {
    List<Publish> findByPublishedDateBetween(LocalDate startDate, LocalDate endDate);

    default Publish findByIdElseThrow(Long publishId) {
        return findById(publishId).orElseThrow(() -> new NotFoundException(ErrorCode.PUBLISH_NOT_FOUND));
    }

    List<Publish> findByProblemSetId(Long problemSetId);

    default void existsByIdElseThrow(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException(ErrorCode.PUBLISH_NOT_FOUND);
        }
    }
}
