package com.moplus.moplus_server.client.submit.repository;

import com.moplus.moplus_server.client.submit.domain.ProblemSubmit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemSubmitRepository extends JpaRepository<ProblemSubmit, Long> {
    List<ProblemSubmit> findByMemberIdAndPublishId(Long memberId, Long publishId);
}
