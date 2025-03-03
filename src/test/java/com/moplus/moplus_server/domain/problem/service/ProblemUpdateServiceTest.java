package com.moplus.moplus_server.domain.problem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemCustomId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("h2test")
@Sql({"/practice-test-tag.sql", "/concept-tag.sql", "/insert-problem.sql"})
@SpringBootTest
class ProblemUpdateServiceTest {

    @Autowired
    private ProblemUpdateService problemUpdateService;

    @Autowired
    private ProblemRepository problemRepository;

    private ProblemCustomId problemCustomId;
    private ProblemUpdateRequest problemUpdateRequest;

    @BeforeEach
    void setUp() {
        problemCustomId = new ProblemCustomId("240520012001");

        //  기존 자식 문제 업데이트
        ChildProblemUpdateRequest updateChildProblem1 = new ChildProblemUpdateRequest(
                1L,
                "updatedChild1.png",
                AnswerType.MULTIPLE_CHOICE,
                "2",
                Set.of(2L, 3L),
                List.of("prescription1.png", "prescription2.png")
        );

        ChildProblemUpdateRequest updateChildProblem2 = new ChildProblemUpdateRequest(
                2L,
                "updatedChild2.png",
                AnswerType.SHORT_ANSWER,
                "23",
                Set.of(3L, 4L),
                List.of("prescription3.png")
        );

        problemUpdateRequest = new ProblemUpdateRequest(
                ProblemType.VARIANT_PROBLEM,
                2L,
                10,
                Set.of(1L, 2L, 3L),
                "정답",
                "업데이트된 제목",
                3,
                "업데이트된 메모",
                "updatedMainProblem.png",
                "updatedMainAnalysis.png",
                "updatedMainHandwriting.png",
                "updatedReadingTip.png",
                "updatedSeniorTip.png",
                List.of("prescription1.png", "prescription2.png"),
                AnswerType.SHORT_ANSWER,
                List.of(updateChildProblem1, updateChildProblem2),
                30,
                45
        );
    }

    @Nested
    class 문제_업데이트_정상_동작 {

        @Test
        void 문제_업데이트_성공() {
            // when
            ProblemGetResponse response = problemUpdateService.updateProblem(1L,
                    problemUpdateRequest);

            // then
            assertThat(response).isNotNull();
            assertThat(response.problemCustomId()).startsWith("22230310"); //  문제 ID 확인
            assertThat(response.problemType()).isEqualTo(ProblemType.VARIANT_PROBLEM);
            assertThat(response.practiceTestId()).isEqualTo(2L);
            assertThat(response.number()).isEqualTo(10);
            assertThat(response.conceptTagIds()).containsExactlyInAnyOrderElementsOf(Set.of(1L, 2L, 3L));
            assertThat(response.answer()).isEqualTo("정답");
            assertThat(response.title()).isEqualTo("업데이트된 제목");
            assertThat(response.difficulty()).isEqualTo(3);
            assertThat(response.memo()).isEqualTo("업데이트된 메모");

            // 이미지 URL 검증
            assertThat(response.mainProblemImageUrl()).isEqualTo("updatedMainProblem.png");
            assertThat(response.mainAnalysisImageUrl()).isEqualTo("updatedMainAnalysis.png");
            assertThat(response.readingTipImageUrl()).isEqualTo("updatedReadingTip.png");
            assertThat(response.seniorTipImageUrl()).isEqualTo("updatedSeniorTip.png");
            assertThat(response.mainHandwritingExplanationImageUrl())
                    .isEqualTo("updatedMainHandwriting.png");
            assertThat(response.prescriptionImageUrls())
                    .containsExactly("prescription1.png", "prescription2.png");

            // 답안 유형 검증
            assertThat(response.answerType()).isEqualTo(AnswerType.SHORT_ANSWER);

            Problem updatedProblem = problemRepository.findByIdElseThrow(1L);

            // 자식 문제 검증
            List<ChildProblem> childProblems = updatedProblem.getChildProblems();
            assertThat(childProblems).hasSize(2);

            // 첫 번째 자식 문제 검증 (업데이트된 기존 문제)
            ChildProblem updatedChild = childProblems.get(0);
            assertThat(updatedChild.getImageUrl()).isEqualTo("updatedChild1.png");
            assertThat(updatedChild.getAnswerType()).isEqualTo(AnswerType.MULTIPLE_CHOICE);
            assertThat(updatedChild.getAnswer()).isEqualTo("2");
            assertThat(updatedChild.getConceptTagIds()).containsExactlyInAnyOrderElementsOf(Set.of(2L, 3L));
            assertThat(updatedChild.getPrescriptionImageUrls())
                    .containsExactly("prescription1.png", "prescription2.png");

            // 두 번째 자식 문제 검증 (새로 추가된 문제)
            ChildProblem newChild = childProblems.get(1);
            assertThat(newChild.getImageUrl()).isEqualTo("updatedChild2.png");
            assertThat(newChild.getAnswerType()).isEqualTo(AnswerType.SHORT_ANSWER);
            assertThat(newChild.getAnswer()).isEqualTo("23");
            assertThat(newChild.getConceptTagIds()).containsExactlyInAnyOrderElementsOf(Set.of(3L, 4L));
            assertThat(newChild.getPrescriptionImageUrls()).containsExactly("prescription3.png");

            // 추가된 검증
            assertThat(response.recommendedMinute()).isEqualTo(30);
            assertThat(response.recommendedSecond()).isEqualTo(45);
        }
    }

    @Nested
    class 문제_업데이트_예외_처리 {

        @Test
        void 문제_업데이트_실패_존재하지_않는_ID() {
            // given
            String invalidId = "999999999999"; // 존재하지 않는 문제 ID

            // when & then
            assertThatThrownBy(() -> problemUpdateService.updateProblem(9999L, problemUpdateRequest))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 문제_업데이트_실패_잘못된_ConceptTag() {
            // given
            ProblemUpdateRequest invalidRequest = new ProblemUpdateRequest(
                    ProblemType.GICHUL_PROBLEM,
                    1L,
                    20,
                    Set.of(999L, 1000L),
                    "정답",
                    "잘못된 제목",
                    3,
                    "잘못된 메모",
                    "updatedMainProblem.png",
                    "updatedMainAnalysis.png",
                    "updatedMainHandwriting.png",
                    "updatedReadingTip.png",
                    "updatedSeniorTip.png",
                    List.of("prescription1.png"),
                    AnswerType.SHORT_ANSWER,
                    List.of(),
                    30,
                    45
            );

            // when & then
            assertThatThrownBy(() -> problemUpdateService.updateProblem(9999L, invalidRequest))
                    .isInstanceOf(NotFoundException.class);
        }
    }
}
