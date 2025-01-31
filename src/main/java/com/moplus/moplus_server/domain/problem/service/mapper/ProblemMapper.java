package com.moplus.moplus_server.domain.problem.service.mapper;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProblemMapper {

    @Mappings({
            @Mapping(target = "id", source = "problemId"),
            @Mapping(target = "practiceTestTag", source = "practiceTestTag"),
    })
    Problem from(ProblemPostRequest request, ProblemId problemId, PracticeTestTag practiceTestTag);

    @Mappings({
            @Mapping(target = "id", source = "problemId"),
            @Mapping(target = "practiceTestTag", source = "practiceTestTag"),
    })
    Problem from(ProblemUpdateRequest request, ProblemId problemId, PracticeTestTag practiceTestTag);
}
