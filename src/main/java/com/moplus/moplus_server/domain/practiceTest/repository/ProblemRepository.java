package com.moplus.moplus_server.domain.practiceTest.repository;

import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.domain.Problem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findAllByPracticeTestId(Long id);

    void deleteAllByPracticeTestId(Long id);

    Optional<Problem> findByProblemNumberAndPracticeTestId(String problemNumber, Long practiceTest_id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Problem p WHERE p.problemNumber = :problem_number AND p.practiceTest.id = :practice_test_id")
    Optional<Problem> findByProblemNumberAndPracticeTestIdWithPessimisticLock(@Param("problem_number") String problemNumber,@Param("practice_test_id")  Long practiceTest_id);
}
