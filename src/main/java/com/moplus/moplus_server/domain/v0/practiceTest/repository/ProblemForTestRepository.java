package com.moplus.moplus_server.domain.v0.practiceTest.repository;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemForTest;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProblemForTestRepository extends JpaRepository<ProblemForTest, Long> {

    List<ProblemForTest> findAllByPracticeTestId(Long id);

    void deleteAllByPracticeTestId(Long id);

    Optional<ProblemForTest> findByProblemNumberAndPracticeTestId(String problemNumber, Long practiceTest_id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProblemForTest p WHERE p.problemNumber = :problem_number AND p.practiceTest.id = :practice_test_id")
    Optional<ProblemForTest> findByProblemNumberAndPracticeTestIdWithPessimisticLock(
            @Param("problem_number") String problemNumber, @Param("practice_test_id") Long practiceTest_id);
}
