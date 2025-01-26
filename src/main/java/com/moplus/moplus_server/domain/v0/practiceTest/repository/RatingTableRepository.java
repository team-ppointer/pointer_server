package com.moplus.moplus_server.domain.v0.practiceTest.repository;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.RatingTable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingTableRepository extends JpaRepository<RatingTable, Long> {
    List<RatingTable> findAllByPracticeTestId(Long practiceTestId);

    void deleteAllByPracticeTestId(Long practiceTestId);
}
