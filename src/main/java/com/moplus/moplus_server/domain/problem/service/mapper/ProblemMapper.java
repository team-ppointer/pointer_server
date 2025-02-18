package com.moplus.moplus_server.domain.problem.service.mapper;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemCustomId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProblemMapper {

    @Mappings({
            @Mapping(target = "problemCustomId", source = "problemCustomId"),
            @Mapping(target = "practiceTestTag", source = "practiceTestTag"),
    })
    Problem from(ProblemPostRequest request, ProblemCustomId problemCustomId, PracticeTestTag practiceTestTag);

    @Mappings({
            @Mapping(target = "problemCustomId", source = "problemCustomId"),
            @Mapping(target = "practiceTestTag", source = "practiceTestTag"),
            @Mapping(target = "recommendedMinute", source = "request.recommendedMinute"),
            @Mapping(target = "recommendedSecond", source = "request.recommendedSecond")
    })
    Problem from(ProblemUpdateRequest request, ProblemCustomId problemCustomId, PracticeTestTag practiceTestTag);

    @Mappings({
            @Mapping(target = "problemCustomId", source = "problemCustomId")
    })
    Problem from(ProblemType problemType, ProblemCustomId problemCustomId);
}
