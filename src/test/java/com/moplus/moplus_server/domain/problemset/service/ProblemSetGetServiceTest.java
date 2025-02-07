package com.moplus.moplus_server.domain.problemset.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemId;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.domain.problem.repository.PracticeTestTagRepository;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.dto.response.ProblemSetGetResponse;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("h2test")
@Sql({"/practice-test-tag.sql", "/concept-tag.sql"})
@SpringBootTest
public class ProblemSetGetServiceTest {

    @Autowired
    private ProblemSetGetService problemSetGetService;

    @Autowired
    private ProblemSetRepository problemSetRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private PracticeTestTagRepository practiceTestTagRepository;

    private ProblemSet savedProblemSet;
    private Problem savedProblem;

    @BeforeEach
    void setUp() {
        // 문제 생성 요청 데이터 준비
        ProblemPostRequest problemPostRequest = new ProblemPostRequest(
                Set.of(1L, 2L),
                1L,
                1,
                "1",
                "문제 설명",
                "mainProblem.png",
                "mainAnalysis.png",
                "readingTip.png",
                "seniorTip.png",
                "prescription.png",
                List.of()
        );

        // 문제 저장
        ProblemId createdProblemId = problemRepository.save(
                new Problem(
                        new ProblemId("24052001001"),
                        practiceTestTagRepository.findByIdElseThrow(1L),
                        1,
                        "1",
                        "문제 설명",
                        "mainProblem.png",
                        "mainAnalysis.png",
                        "readingTip.png",
                        "seniorTip.png",
                        "prescription.png",
                        Set.of(1L, 2L)
                )
        ).getId();

        savedProblem = problemRepository.findByIdElseThrow(createdProblemId);

        // 문항세트 저장
        savedProblemSet = problemSetRepository.save(
                new ProblemSet("테스트 문항세트", List.of(savedProblem.getId()))
        );
    }

    @Test
    void 문항세트_조회_성공_테스트() {
        // when
        ProblemSetGetResponse response = problemSetGetService.getProblemSet(savedProblemSet.getId());

        // then
        assertThat(response).isNotNull();
        assertThat(response.title()).isEqualTo("테스트 문항세트");
        assertThat(response.problemSummaries()).hasSize(1);
        assertThat(response.problemSummaries().get(0).problemId()).isEqualTo(savedProblem.getId().toString());
        assertThat(response.problemSummaries().get(0).practiceTestName()).isEqualTo("2025년 5월 고2 모의고사");
        assertThat(response.problemSummaries().get(0).tagNames()).contains("미분 개념", "적분 개념");
    }

    @Test
    void 존재하지_않는_문항세트_조회_실패_테스트() {
        // when & then
        assertThatThrownBy(() -> problemSetGetService.getProblemSet(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("해당 문항세트를 찾을 수 없습니다");
    }

    @Test
    void 문항세트_조회_성공_테스트_여러개() {
        // given: 여러 개의 문제를 저장
        Problem savedProblem2 = problemRepository.save(
                new Problem(
                        new ProblemId("24052001002"),
                        practiceTestTagRepository.findByIdElseThrow(1L),
                        2,
                        "2",
                        "문제 설명2",
                        "mainProblem2.png",
                        "mainAnalysis2.png",
                        "readingTip2.png",
                        "seniorTip2.png",
                        "prescription2.png",
                        Set.of(3L, 4L)
                )
        );

        ProblemSet multipleProblemSet = problemSetRepository.save(
                new ProblemSet("여러 문항 테스트 문항세트", List.of(savedProblem.getId(), savedProblem2.getId()))
        );

        // when
        ProblemSetGetResponse response = problemSetGetService.getProblemSet(multipleProblemSet.getId());

        // then
        assertThat(response).isNotNull();
        assertThat(response.title()).isEqualTo("여러 문항 테스트 문항세트");
        assertThat(response.problemSummaries()).hasSize(2);

        // 첫 번째 문제 검증
        assertThat(response.problemSummaries().get(0).problemId()).isEqualTo(savedProblem.getId().toString());
        assertThat(response.problemSummaries().get(0).practiceTestName()).isEqualTo("2025년 5월 고2 모의고사");
        assertThat(response.problemSummaries().get(0).tagNames()).contains("미분 개념", "적분 개념");

        // 두 번째 문제 검증
        assertThat(response.problemSummaries().get(1).problemId()).isEqualTo(savedProblem2.getId().toString());
        assertThat(response.problemSummaries().get(1).practiceTestName()).isEqualTo("2025년 5월 고2 모의고사");
        assertThat(response.problemSummaries().get(1).tagNames()).contains("삼각함수 개념", "행렬 개념");
    }
}