package com.moplus.moplus_server.domain.problem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moplus.moplus_server.domain.problem.dto.response.ProblemSearchGetResponse;
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
@Sql({"/practice-test-tag.sql", "/concept-tag.sql", "/insert-problem.sql"})
public class ProblemSearchRepositoryImplTest {

    @Autowired
    private ProblemRepository problemSearchRepository;

    @Test
    void problemId_일부_포함_검색() {
        // when
        List<ProblemSearchGetResponse> result = problemSearchRepository.search("240520012", null, null);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(ProblemSearchGetResponse::getProblemId)
                .containsExactlyInAnyOrder("240520012001", "240520012002");
    }

    @Test
    void name_포함_검색() {
        // when
        List<ProblemSearchGetResponse> result = problemSearchRepository.search(null, "설명 1 ", null);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProblemId()).isEqualTo("240520012001");
    }

    @Test
    void conceptTagIds_하나라도_포함되면_조회() {
        // when
        List<ProblemSearchGetResponse> result = problemSearchRepository.search(null, null, List.of(3L));

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(ProblemSearchGetResponse::getProblemId)
                .containsExactlyInAnyOrder("240520012001", "240520012002");
    }

    @Test
    void problemId_이름_conceptTagIds_모두_적용된_검색() {
        // when
        List<ProblemSearchGetResponse> result = problemSearchRepository.search("2405200120", "설명 1", List.of(1L));

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProblemId()).isEqualTo("240520012001");
    }

    @Test
    void 아무_조건도_없으면_모든_데이터_조회() {
        // when
        List<ProblemSearchGetResponse> result = problemSearchRepository.search(null, null, null);

        // then
        assertThat(result).hasSize(2);
    }
}