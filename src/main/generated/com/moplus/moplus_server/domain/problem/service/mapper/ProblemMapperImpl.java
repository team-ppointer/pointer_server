package com.moplus.moplus_server.domain.problem.service.mapper;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-13T05:08:28+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class ProblemMapperImpl implements ProblemMapper {

    @Override
    public Problem from(ProblemPostRequest request, ProblemAdminId problemAdminId, PracticeTestTag practiceTestTag) {
        if ( request == null && problemAdminId == null && practiceTestTag == null ) {
            return null;
        }

        Problem.ProblemBuilder problem = Problem.builder();

        if ( request != null ) {
            problem.problemType( request.problemType() );
            problem.number( request.number() );
        }
        problem.problemAdminId( problemAdminId );
        problem.practiceTestTag( practiceTestTag );

        return problem.build();
    }

    @Override
    public Problem from(ProblemUpdateRequest request, ProblemAdminId problemAdminId, PracticeTestTag practiceTestTag) {
        if ( request == null && problemAdminId == null && practiceTestTag == null ) {
            return null;
        }

        Problem.ProblemBuilder problem = Problem.builder();

        if ( request != null ) {
            problem.answerType( request.answerType() );
            Set<Long> set = request.conceptTagIds();
            if ( set != null ) {
                problem.conceptTagIds( new LinkedHashSet<Long>( set ) );
            }
            problem.difficulty( request.difficulty() );
            problem.mainHandwritingExplanationImageUrl( request.mainHandwritingExplanationImageUrl() );
            List<String> list = request.prescriptionImageUrls();
            if ( list != null ) {
                problem.prescriptionImageUrls( new ArrayList<String>( list ) );
            }
            problem.seniorTipImageUrl( request.seniorTipImageUrl() );
            problem.readingTipImageUrl( request.readingTipImageUrl() );
            problem.mainAnalysisImageUrl( request.mainAnalysisImageUrl() );
            problem.mainProblemImageUrl( request.mainProblemImageUrl() );
            problem.memo( request.memo() );
            problem.answer( request.answer() );
            problem.title( request.title() );
            problem.problemType( request.problemType() );
            problem.number( request.number() );
        }
        problem.problemAdminId( problemAdminId );
        problem.practiceTestTag( practiceTestTag );

        return problem.build();
    }
}
