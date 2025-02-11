package com.moplus.moplus_server.domain.problem.service.mapper;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProblemMapper {

    @Mappings({
            @Mapping(target = "problemAdminId", source = "problemAdminId"),
            @Mapping(target = "practiceTestTag", source = "practiceTestTag"),
    })
    Problem from(ProblemPostRequest request, ProblemAdminId problemAdminId, PracticeTestTag practiceTestTag);

    @Mappings({
            @Mapping(target = "problemAdminId", source = "problemAdminId"),
            @Mapping(target = "practiceTestTag", source = "practiceTestTag"),
    })
    Problem from(ProblemUpdateRequest request, ProblemAdminId problemAdminId, PracticeTestTag practiceTestTag);
}
