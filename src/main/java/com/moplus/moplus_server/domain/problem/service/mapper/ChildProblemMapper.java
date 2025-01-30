package com.moplus.moplus_server.domain.problem.service;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChildProblemMapper {

    ChildProblem from(ChildProblemPostRequest request);

    ChildProblem from(ChildProblemUpdateRequest request);
}
