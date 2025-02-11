package com.moplus.moplus_server.domain.problem.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemUpdateRequest;
import com.moplus.moplus_server.domain.problem.dto.response.ProblemGetResponse;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
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

        // 🔹 새 자식 문제 추가
        ChildProblemUpdateRequest newChildProblem = new ChildProblemUpdateRequest(
                null,
                "newChild.png",
                AnswerType.SHORT_STRING_ANSWER,
                "새로운 정답",
                Set.of(1L, 2L),
                1
        );

        // 🔹 기존 자식 문제 업데이트
        ChildProblemUpdateRequest updateChildProblem = new ChildProblemUpdateRequest(
                1L, // 기존 자식 문제 ID
                "updatedChild.png",
                AnswerType.MULTIPLE_CHOICE,
                "2",
                Set.of(2L, 3L),
                0
        );

        // 🔹 기존 자식 문제 삭제
        List<Long> deleteChildProblem = List.of(2L); // 삭제할 자식 문제 ID

        problemUpdateRequest = new ProblemUpdateRequest(
                Set.of(1L, 2L, 3L), // 업데이트할 부모 문제의 Concept Tags
                1, // 문제 정답
                "수정된 설명", // 새로운 설명
                "updatedMainProblem.png",
                "updatedMainAnalysis.png",
                "updatedReadingTip.png",
                "updatedSeniorTip.png",
                "updatedPrescription.png",
                List.of(newChildProblem, updateChildProblem), // 업데이트할 자식 문제
                deleteChildProblem // 삭제할 자식 문제
        );
    }

    @Test
    void 문제_업데이트_정상동작() {
        // when
        ProblemGetResponse response = problemUpdateService.updateProblem(problemAdminId.getId(),
                problemUpdateRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.comment()).isEqualTo("수정된 설명"); // ✅ 설명이 변경되었는지 검증
        assertThat(response.mainProblemImageUrl()).isEqualTo("updatedMainProblem.png"); // ✅ 이미지 URL 변경 확인

        Problem updatedProblem = problemRepository.findByIdElseThrow(problemAdminId);

        // ✅ 자식 문제 개수 검증
        List<ChildProblem> childProblems = updatedProblem.getChildProblems();
        assertThat(childProblems).hasSize(2); // 기존 2개 → 1개 삭제, 1개 추가 후 2개

        // ✅ 부모 문제의 conceptTagIds가 자식 문제의 conceptTagIds를 모두 포함하는지 검증
        Set<Long> problemTags = updatedProblem.getConceptTagIds();
        updatedProblem.getChildProblems().forEach(child -> {
            assertThat(problemTags).containsAll(child.getConceptTagIds());
        });

        // ✅ 자식 문제 순서가 올바르게 정렬되었는지 확인
        IntStream.range(0, childProblems.size()).forEach(i -> {
            assertThat(childProblems.get(i).getSequence()).isEqualTo(i);
        });

        // ✅ 개별 자식 문제 검증
        assertThat(childProblems.get(0).getImageUrl()).isEqualTo("updatedChild.png"); // 기존 자식 문제 업데이트 확인
        assertThat(childProblems.get(1).getImageUrl()).isEqualTo("newChild.png"); // 새 자식 문제 추가 확인
    }
}