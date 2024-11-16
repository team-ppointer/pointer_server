package com.moplus.moplus_server.domain.practiceTest.repository;

import com.moplus.moplus_server.domain.practiceTest.domain.ProblemImage;
import java.util.Optional;
import javax.swing.JPanel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemImageRepository extends JpaRepository<ProblemImage, Long> {

    Optional<ProblemImage> findByProblemId(Long problemId);
}
