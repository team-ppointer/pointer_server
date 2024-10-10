package com.moplus.moplus_server.domain.practiceTest.repository;

import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeTestRepository extends JpaRepository<PracticeTest, Long> {
    List<PracticeTest> findAllByOrderByViewCountDesc();
}
