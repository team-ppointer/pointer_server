package com.moplus.moplus_server.domain.practiceTest.repository;

import com.moplus.moplus_server.domain.practiceTest.domain.RatingTable;
import com.moplus.moplus_server.domain.practiceTest.domain.Subject;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingTableRepository extends JpaRepository<RatingTable, Long> {
    Optional<RatingTable> findByPracticeTestIdAndSubject(Long practiceTestId, Subject subject);
}
