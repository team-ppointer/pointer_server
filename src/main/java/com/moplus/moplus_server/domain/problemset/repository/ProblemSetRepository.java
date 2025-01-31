package com.moplus.moplus_server.domain.problemset.repository;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSetRepository extends JpaRepository<ProblemSet, Long> {

}
