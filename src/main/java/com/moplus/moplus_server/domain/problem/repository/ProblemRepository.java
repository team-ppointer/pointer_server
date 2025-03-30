package com.moplus.moplus_server.domain.problem.repository;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemCustomId;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Logger log = LoggerFactory.getLogger(ProblemRepository.class);

    boolean existsByProblemCustomId(ProblemCustomId problemCustomId);

    default void existsByIdElseThrow(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND);
        }
    }

    @Query("SELECT DISTINCT p FROM Problem p " +
            "LEFT JOIN FETCH p.childProblems c " +
            "WHERE p.id = :id")
    Optional<Problem> findByIdWithFetchJoin(@Param("id") Long id);

    default Problem findByIdElseThrow(Long id) {

        return findById(id).orElseThrow(
                () -> {
                    log.atError().log("id " + id + "번 문항이 존재하지 않습니다.");
                    throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND);
                });
    }

    default Problem findByIdWithFetchJoinElseThrow(Long id) {
        return findByIdWithFetchJoin(id).orElseThrow(() -> {
            log.atError().log("id " + id + "번 문항이 존재하지 않습니다.");
            throw new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND);
        });
    }
}
