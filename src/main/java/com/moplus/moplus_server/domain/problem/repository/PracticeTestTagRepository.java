package com.moplus.moplus_server.domain.problem.repository;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTest;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeTestTagRepository extends JpaRepository<PracticeTest, Long> {

    default PracticeTest findByIdElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));
    }
}
