package com.moplus.moplus_server.domain.problem.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.moplus.moplus_server.domain.problem.domain.childProblem.ChildProblem;
import com.moplus.moplus_server.domain.problem.domain.problem.AnswerType;
import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemAdminId;
import com.moplus.moplus_server.domain.problem.dto.request.ChildProblemPostRequest;
import com.moplus.moplus_server.domain.problem.dto.request.ProblemPostRequest;
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
@Sql({"/practice-test-tag.sql", "/concept-tag.sql"})
@SpringBootTest
class ProblemSaveServiceTest {

    @Autowired
    private ProblemSaveService problemSaveService;

    @Autowired
    private ProblemRepository problemRepository;

    private ProblemPostRequest problemPostRequestOutOfOrder;
    private ProblemPostRequest problemPostRequestInOrder;

    @BeforeEach
    void setUp() {
        // 🔹 1. 일부러 순서를 뒤죽박죽으로 설정한 문제
        ChildProblemPostRequest childProblem1 = new ChildProblemPostRequest(
                "child1.png", AnswerType.SHORT_STRING_ANSWER, "정답1", Set.of(3L, 4L), 3
        );
        ChildProblemPostRequest childProblem2 = new ChildProblemPostRequest(
                "child2.png", AnswerType.MULTIPLE_CHOICE, "1", Set.of(5L, 6L), 1
        );
        ChildProblemPostRequest childProblem3 = new ChildProblemPostRequest(
                "child3.png", AnswerType.MULTIPLE_CHOICE, "2", Set.of(3L, 4L), 0
        );
        ChildProblemPostRequest childProblem4 = new ChildProblemPostRequest(
                "child4.png", AnswerType.SHORT_NUMBER_ANSWER, "0", Set.of(1L, 2L), 2
        );

        problemPostRequestOutOfOrder = new ProblemPostRequest(
                Set.of(1L, 2L),
                1L,
                21,
                "1",
                "설명",
                "mainProblem.png",
                "mainAnalysis.png",
                "readingTip.png",
                "seniorTip.png",
                "prescription.png",
                List.of(childProblem1, childProblem2, childProblem3, childProblem4) // 🔹 순서 뒤죽박죽
        );

        // 🔹 2. 순서가 올바른 상태에서 입력되는 문제
        problemPostRequestInOrder = new ProblemPostRequest(
                Set.of(1L, 2L),
                1L,
                20,
                "2",
                "다른 설명",
                "mainProblem2.png",
                "mainAnalysis2.png",
                "readingTip2.png",
                "seniorTip2.png",
                "prescription2.png",
                List.of(childProblem3, childProblem2, childProblem4, childProblem1) // 🔹 순서 유지 (0,1,2,3)
        );

    }

    @Test
    void 정상동작() {

        // when
        ProblemAdminId createdProblemAdminId = problemSaveService.createProblem(problemPostRequestInOrder);

        // then
        assertThat(createdProblemAdminId).isNotNull();
        assertThat(createdProblemAdminId.getId()).startsWith("2405200120"); // ID 앞부분 확인

        Problem savedProblem = problemRepository.findByIdElseThrow(createdProblemAdminId);

        // 모든 자식 문제의 conceptTagIds가 부모 문제의 conceptTagIds에 포함되는지 검증
        Set<Long> problemTags = savedProblem.getConceptTagIds();
        problemPostRequestInOrder.childProblems().forEach(child -> {
            assertThat(problemTags).containsAll(child.conceptTagIds());
        });

        // 자식 문제의 순서 검증
        List<ChildProblem> childProblems = savedProblem.getChildProblems();

        assertThat(childProblems).hasSize(4); // 자식 문제 개수 검증

        // 저장된 자식 문제가 원래 요청한 `sequence` 순서와 같은지 확인
        IntStream.range(0, childProblems.size()).forEach(i -> {
            assertThat(childProblems.get(i).getSequence()).isEqualTo(i);
        });
    }

    @Test
    void 자식문제_올바른_순서_저장() {
        // when
        ProblemAdminId createdProblemAdminId = problemSaveService.createProblem(problemPostRequestOutOfOrder);

        // then
        assertThat(createdProblemAdminId).isNotNull();
        assertThat(createdProblemAdminId.getId()).startsWith("2405210120"); // ID 앞부분 확인

        // 저장된 문제 조회
        Problem savedProblem = problemRepository.findByIdElseThrow(createdProblemAdminId);

        // ✅ 모든 자식 문제의 conceptTagIds가 부모 문제의 conceptTagIds에 포함되는지 검증
        Set<Long> problemTags = savedProblem.getConceptTagIds();
        problemPostRequestOutOfOrder.childProblems().forEach(child -> {
            assertThat(problemTags).containsAll(child.conceptTagIds());
        });

        // ✅ 자식 문제의 순서 검증
        List<ChildProblem> childProblems = savedProblem.getChildProblems();

        assertThat(childProblems).hasSize(4); // 자식 문제 개수 검증

        // 🔹 저장된 자식 문제들이 `sequence` 오름차순으로 정렬되었는지 확인
        IntStream.range(0, childProblems.size()).forEach(i -> {
            assertThat(childProblems.get(i).getSequence()).isEqualTo(i);
        });

        // 🔹 정렬 후 올바른 문제인지 검증
        assertThat(childProblems.get(0).getImageUrl()).isEqualTo("child3.png"); // sequence 0
        assertThat(childProblems.get(1).getImageUrl()).isEqualTo("child2.png"); // sequence 1
        assertThat(childProblems.get(2).getImageUrl()).isEqualTo("child4.png"); // sequence 2
        assertThat(childProblems.get(3).getImageUrl()).isEqualTo("child1.png"); // sequence 3
    }
}