package com.moplus.moplus_server.domain.problemset.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moplus.moplus_server.domain.problemset.dto.response.ProblemSetSearchGetResponse;
import com.moplus.moplus_server.domain.problemset.dto.response.ProblemThumbnailResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("h2test")
@Sql({"/practice-test-tag.sql", "/concept-tag.sql", "/insert-problem.sql", "/insert-problem-set.sql"})
public class ProblemSetSearchRepositoryCustomTest {

    @Autowired
    private ProblemSetSearchRepositoryCustom problemSetSearchRepository;

    @Test
    void 문항세트_타이틀_일부_포함_검색() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search("고2 모의고사", null, null);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProblemSetTitle()).isEqualTo("2025년 5월 고2 모의고사 문제 세트");
    }

    @Test
    void 문항타이틀_포함_검색() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search(null, "설명 1", null);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProblemSetTitle()).isEqualTo("2025년 5월 고2 모의고사 문제 세트");
    }

    @Test
    void 개념태그_하나라도_포함되면_조회() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search(null, null, List.of("미분 개념"));

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProblemSetTitle()).isEqualTo("2025년 5월 고2 모의고사 문제 세트");
    }

    @Test
    void 모두_적용된_검색() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search("고2", "설명 1", List.of("미분 개념"));

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProblemSetTitle()).isEqualTo("2025년 5월 고2 모의고사 문제 세트");
    }

    @Test
    void 아무_조건도_없으면_모든_데이터_조회() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search(null, null, null);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void 문항_여러개_문항세트_검색_조회() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search("고2 모의고사", null, null);

        // then
        assertThat(result).hasSize(1);
        ProblemSetSearchGetResponse response = result.get(0);
        assertThat(response.getProblemSetTitle()).isEqualTo("2025년 5월 고2 모의고사 문제 세트");

        // ✅ 문항이 2개 존재하는지 확인
        List<ProblemThumbnailResponse> problems = response.getProblemThumbnailResponses();
        assertThat(problems).hasSize(2);

        // ✅ 문항의 이미지 URL이 올바르게 매핑되었는지 확인
        assertThat(problems.get(0).getMainProblemImageUrl()).isEqualTo("mainProblem.png1");
        assertThat(problems.get(1).getMainProblemImageUrl()).isEqualTo("mainProblem.png2");
    }
}