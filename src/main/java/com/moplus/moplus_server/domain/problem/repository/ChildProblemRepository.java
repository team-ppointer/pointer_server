package com.moplus.moplus_server.domain.problem.repository;

import com.moplus.moplus_server.domain.problem.domain.ChildProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildProblemRepository extends JpaRepository<ChildProblem, Long> {
}
