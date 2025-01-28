package com.moplus.moplus_server.domain.v0.practiceTest.repository;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemImageForTest;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemImageRepository extends JpaRepository<ProblemImageForTest, Long> {

    Optional<ProblemImageForTest> findByProblemId(Long problemId);
}
