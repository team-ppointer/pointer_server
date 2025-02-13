package com.moplus.moplus_server.domain.problem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
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

    private ProblemAdminId problemAdminId;
    private ProblemUpdateRequest problemUpdateRequest;

    @BeforeEach
    void setUp() {
        problemAdminId = new ProblemAdminId("240520012001");

        //  새 자식 문제 추가
        ChildProblemUpdateRequest newChildProblem = new ChildProblemUpdateRequest(
                null,
                "newChild.png",
                AnswerType.SHORT_STRING_ANSWER,
                "새로운 정답",
                Set.of(1L, 2L),
                1
        );

        //  기존 자식 문제 업데이트
        ChildProblemUpdateRequest updateChildProblem = new ChildProblemUpdateRequest(
                1L, // 기존 자식 문제 ID
                "updatedChild.png",
                AnswerType.MULTIPLE_CHOICE,
                "2",
                Set.of(2L, 3L),
                0
        );

        //  기존 자식 문제 삭제
        List<Long> deleteChildProblem = List.of(2L); // 삭제할 자식 문제 ID

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
                "updatedMainHandwriting.png", // 추가
                "updatedReadingTip.png",
                "updatedSeniorTip.png",
                List.of("prescription1.png", "prescription2.png"), // List<String>으로 변경
                AnswerType.SHORT_STRING_ANSWER,
                List.of(newChildProblem, updateChildProblem),
                deleteChildProblem
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
            assertThat(response.problemId()).startsWith("22230310"); //  문제 ID 확인
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
            assertThat(response.answerType()).isEqualTo(AnswerType.SHORT_STRING_ANSWER);

            Problem updatedProblem = problemRepository.findByIdElseThrow(1L);

            // 자식 문제 검증
            List<ChildProblem> childProblems = updatedProblem.getChildProblems();
            assertThat(childProblems).hasSize(2); // 기존 2개 → 1개 삭제, 1개 추가 후 2개

            // 첫 번째 자식 문제 검증 (업데이트된 기존 문제)
            ChildProblem updatedChild = childProblems.get(0);
            assertThat(updatedChild.getId()).isEqualTo(1L);
            assertThat(updatedChild.getImageUrl()).isEqualTo("updatedChild.png");
            assertThat(updatedChild.getAnswerType()).isEqualTo(AnswerType.MULTIPLE_CHOICE);
            assertThat(updatedChild.getAnswer()).isEqualTo("2");
            assertThat(updatedChild.getConceptTagIds()).containsExactlyInAnyOrderElementsOf(Set.of(2L, 3L));
            assertThat(updatedChild.getSequence()).isEqualTo(0);

            // 두 번째 자식 문제 검증 (새로 추가된 문제)
            ChildProblem newChild = childProblems.get(1);
            assertThat(newChild.getImageUrl()).isEqualTo("newChild.png");
            assertThat(newChild.getAnswerType()).isEqualTo(AnswerType.SHORT_STRING_ANSWER);
            assertThat(newChild.getAnswer()).isEqualTo("새로운 정답");
            assertThat(newChild.getConceptTagIds()).containsExactlyInAnyOrderElementsOf(Set.of(1L, 2L));
            assertThat(newChild.getSequence()).isEqualTo(1);

            // 부모 문제의 conceptTagIds가 자식 문제의 conceptTagIds를 모두 포함하는지 검증
            Set<Long> problemTags = updatedProblem.getConceptTagIds();
            childProblems.forEach(child -> {
                assertThat(problemTags).containsAll(child.getConceptTagIds());
            });

            // 자식 문제 순서가 올바르게 정렬되었는지 확인
            IntStream.range(0, childProblems.size()).forEach(i -> {
                assertThat(childProblems.get(i).getSequence()).isEqualTo(i);
            });
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
                    "updatedMainHandwriting.png", // 추가
                    "updatedReadingTip.png",
                    "updatedSeniorTip.png",
                    List.of("prescription1.png"), // List<String>으로 변경
                    AnswerType.SHORT_STRING_ANSWER,
                    List.of(),
                    List.of()
            );

            // when & then
            assertThatThrownBy(() -> problemUpdateService.updateProblem(9999L, invalidRequest))
                    .isInstanceOf(NotFoundException.class);
        }
    }
}
