package com.moplus.moplus_server.domain.practiceTest.repository;

import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface PracticeTestRepository extends JpaRepository<PracticeTest, Long> {
    List<PracticeTest> findAllByOrderByViewCountDesc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from PracticeTest s where s.id = :id")
    PracticeTest findByIdWithPessimisticLock(Long id);
}
