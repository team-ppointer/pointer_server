package com.moplus.moplus_server.domain.problem.service.mapper;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemUpdateRequest;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-03T17:28:35+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class ChildProblemMapperImpl implements ChildProblemMapper {

    @Override
    public ChildProblem from(ChildProblemUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        ChildProblem.ChildProblemBuilder childProblem = ChildProblem.builder();

        childProblem.id( request.childProblemId() );
        List<String> list = request.prescriptionImageUrls();
        if ( list != null ) {
            childProblem.prescriptionImageUrls( new ArrayList<String>( list ) );
        }
        childProblem.imageUrl( request.imageUrl() );
        childProblem.answerType( request.answerType() );
        childProblem.answer( request.answer() );
        Set<Long> set = request.conceptTagIds();
        if ( set != null ) {
            childProblem.conceptTagIds( new LinkedHashSet<Long>( set ) );
        }

        return childProblem.build();
    }
}
