package com.moplus.moplus_server.domain.TestResult.repository;

import com.moplus.moplus_server.domain.TestResult.entity.EstimatedRating;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimatedRatingRepository extends JpaRepository<EstimatedRating, Long> {
    List<EstimatedRating> findAllByTestResultId(Long testResultId);
}
