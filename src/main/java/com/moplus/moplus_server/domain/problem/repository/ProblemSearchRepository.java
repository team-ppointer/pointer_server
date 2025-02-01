package com.moplus.moplus_server.domain.problem.repository;

import com.moplus.moplus_server.domain.problem.dto.response.ProblemSearchGetResponse;
import java.util.List;

public interface ProblemSearchRepository {
    List<ProblemSearchGetResponse> search(String problemId, String comment, List<Long> conceptTagIds);
}
