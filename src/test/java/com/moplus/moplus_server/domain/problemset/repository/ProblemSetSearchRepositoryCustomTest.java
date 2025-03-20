package com.moplus.moplus_server.domain.problemset.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import com.moplus.moplus_server.admin.problemset.dto.response.ProblemSetSearchGetResponse;
import com.moplus.moplus_server.admin.problemset.dto.response.ProblemThumbnailResponse;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetUpdateService;
import com.moplus.moplus_server.admin.publish.dto.request.PublishPostRequest;
import com.moplus.moplus_server.admin.publish.service.PublishSaveService;
import java.time.LocalDate;
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

    @Autowired
    private PublishSaveService publishSaveService;

    @Autowired
    private ProblemSetUpdateService problemSetUpdateService;


    @Test
    void 문항세트_타이틀_일부_포함_검색() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search("고2 모의고사", null);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProblemSetTitle()).isEqualTo("2025년 5월 고2 모의고사 문제 세트");
    }

    @Test
    void 문항타이틀_포함_검색() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search(null, "설명");

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getProblemSetTitle()).isEqualTo("2025년 5월 고2 모의고사 문제 세트");
        assertThat(result.get(1).getProblemSetTitle()).isEqualTo("2025년 5월 고3 모의고사 문제 세트");
    }

    @Test
    void 모두_적용된_검색() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search("고2", "설명 1");

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProblemSetTitle()).isEqualTo("2025년 5월 고2 모의고사 문제 세트");
    }

    @Test
    void 아무_조건도_없으면_모든_데이터_조회() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search(null, null);

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    void 문항_여러개_문항세트_검색_조회() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search("고2 모의고사", null);

        // then
        assertThat(result).hasSize(1);
        ProblemSetSearchGetResponse response = result.get(0);
        assertThat(response.getProblemSetTitle()).isEqualTo("2025년 5월 고2 모의고사 문제 세트");

        // ✅ 문항이 2개 존재하는지 확인
        List<ProblemThumbnailResponse> problems = response.getProblemThumbnailResponses();
        assertThat(problems).hasSize(2);

        // ✅ 문항의 타이틀, 메모, 이미지 URL이 올바르게 매핑되었는지 확인
        assertThat(problems.get(0).getProblemTitle()).isEqualTo("제목1");
        assertThat(problems.get(0).getProblemMemo()).isEqualTo("기존 문제 설명 1");
        assertThat(problems.get(0).getMainProblemImageUrl()).isEqualTo("mainProblem.png1");

        assertThat(problems.get(1).getProblemTitle()).isEqualTo("제목2");
        assertThat(problems.get(1).getProblemMemo()).isEqualTo("기존 문제 설명 2");
        assertThat(problems.get(1).getMainProblemImageUrl()).isEqualTo("mainProblem.png2");
    }

    @Test
    void 발행되지_않은_문항세트는_NOT_CONFIRMED_테스트() {
        // when
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.search("고2 모의고사", null);

        // then
        assertThat(result).hasSize(1);
        ProblemSetSearchGetResponse response = result.get(0);

        assertThat(response.getConfirmStatus()).isEqualTo(ProblemSetConfirmStatus.NOT_CONFIRMED);
    }

    @Test
    void 컴펌된_문항세트_검색_테스트() {
        // given: CONFIRMED 상태의 문제 세트만 발행
        LocalDate publishDate = LocalDate.now();
        publishSaveService.createPublish(new PublishPostRequest(publishDate.plusDays(5), 2L)); // CONFIRMED 상태

        // when: publishSearch 실행 (CONFIRMED 상태만 검색되어야 함)
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.confirmSearch(
                "고",
                "설명"
        );

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).allSatisfy(response ->
                assertThat(response.getConfirmStatus()).isEqualTo(ProblemSetConfirmStatus.CONFIRMED)
        );
    }

    @Test
    void 컴펌되지_않은_문항세트_검색_결과없음_테스트() {
        // given: 발행되지 않은 문제 세트 존재
        problemSetUpdateService.toggleConfirmProblemSet(2L);

        // when: 발행된 문제 세트만 조회하는 publishSearch 실행
        List<ProblemSetSearchGetResponse> result = problemSetSearchRepository.confirmSearch(
                null,
                null
        );

        // then: CONFIRMED 상태가 없는 경우, 결과가 비어 있어야 함
        assertThat(result).isEmpty();
    }

}