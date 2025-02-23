package com.moplus.moplus_server.domain.problem.service.mapper;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChildProblemMapper {

    ChildProblem from(ChildProblemPostRequest request);

    @Mapping(target = "id", source = "childProblemId")
    ChildProblem from(ChildProblemUpdateRequest request);
}
