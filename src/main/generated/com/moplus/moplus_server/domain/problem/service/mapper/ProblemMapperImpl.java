package com.moplus.moplus_server.domain.problem.service.mapper;

import com.moplus.moplus_server.admin.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.admin.problem.dto.request.ProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemCustomId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-24T01:02:22+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class ProblemMapperImpl implements ProblemMapper {

    @Override
    public Problem from(ProblemPostRequest request, ProblemCustomId problemCustomId, PracticeTestTag practiceTestTag) {
        if ( request == null && problemCustomId == null && practiceTestTag == null ) {
            return null;
        }

        Problem.ProblemBuilder problem = Problem.builder();

        if ( request != null ) {
            problem.problemType( request.problemType() );
            problem.number( request.number() );
        }
        problem.problemCustomId( problemCustomId );
        problem.practiceTestTag( practiceTestTag );

        return problem.build();
    }

    @Override
    public Problem from(ProblemUpdateRequest request, ProblemCustomId problemCustomId, PracticeTestTag practiceTestTag) {
        if ( request == null && problemCustomId == null && practiceTestTag == null ) {
            return null;
        }

        Problem.ProblemBuilder problem = Problem.builder();

        if ( request != null ) {
            problem.recommendedMinute( request.recommendedMinute() );
            problem.recommendedSecond( request.recommendedSecond() );
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
        problem.problemCustomId( problemCustomId );
        problem.practiceTestTag( practiceTestTag );

        return problem.build();
    }

    @Override
    public Problem from(ProblemType problemType, ProblemCustomId problemCustomId) {
        if ( problemType == null && problemCustomId == null ) {
            return null;
        }

        Problem.ProblemBuilder problem = Problem.builder();

        problem.problemType( problemType );
        problem.problemCustomId( problemCustomId );

        return problem.build();
    }
}
