package com.moplus.moplus_server.domain.TestResult.repository;

import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    List<TestResult> findByPracticeTestIdOrderByScoreDesc(Long practiceTestId);
}
