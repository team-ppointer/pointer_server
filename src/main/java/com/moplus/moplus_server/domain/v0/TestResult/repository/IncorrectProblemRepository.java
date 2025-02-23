package com.moplus.moplus_server.domain.v0.TestResult.repository;

import com.moplus.moplus_server.domain.v0.TestResult.entity.IncorrectProblem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncorrectProblemRepository extends JpaRepository<IncorrectProblem, Long> {

    List<IncorrectProblem> findAllByTestResultId(Long testResultId);
}
