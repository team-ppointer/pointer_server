package com.moplus.moplus_server.domain.problem.service.mapper;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-30T21:11:35+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class ProblemMapperImpl implements ProblemMapper {

    @Override
    public Problem from(ProblemPostRequest request, ProblemId problemId, PracticeTestTag practiceTestTag) {
        if ( request == null && problemId == null && practiceTestTag == null ) {
            return null;
        }

        Problem.ProblemBuilder problem = Problem.builder();

        if ( request != null ) {
            problem.number( request.number() );
            problem.answer( request.answer() );
            problem.comment( request.comment() );
            problem.mainProblemImageUrl( request.mainProblemImageUrl() );
            problem.mainAnalysisImageUrl( request.mainAnalysisImageUrl() );
            problem.readingTipImageUrl( request.readingTipImageUrl() );
            problem.seniorTipImageUrl( request.seniorTipImageUrl() );
            problem.prescriptionImageUrl( request.prescriptionImageUrl() );
            Set<Long> set = request.conceptTagIds();
            if ( set != null ) {
                problem.conceptTagIds( new LinkedHashSet<Long>( set ) );
            }
        }
        problem.id( problemId );
        problem.practiceTestTag( practiceTestTag );

        return problem.build();
    }

    @Override
    public Problem from(ProblemUpdateRequest request, ProblemId problemId, PracticeTestTag practiceTestTag) {
        if ( request == null && problemId == null && practiceTestTag == null ) {
            return null;
        }

        Problem.ProblemBuilder problem = Problem.builder();

        if ( request != null ) {
            problem.answer( String.valueOf( request.answer() ) );
            problem.comment( request.comment() );
            problem.mainProblemImageUrl( request.mainProblemImageUrl() );
            problem.mainAnalysisImageUrl( request.mainAnalysisImageUrl() );
            problem.readingTipImageUrl( request.readingTipImageUrl() );
            problem.seniorTipImageUrl( request.seniorTipImageUrl() );
            problem.prescriptionImageUrl( request.prescriptionImageUrl() );
            Set<Long> set = request.conceptTagIds();
            if ( set != null ) {
                problem.conceptTagIds( new LinkedHashSet<Long>( set ) );
            }
        }
        problem.id( problemId );
        problem.practiceTestTag( practiceTestTag );

        return problem.build();
    }
}
