package com.moplus.moplus_server.domain.problem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.moplus.moplus_server.domain.problem.domain.problem.Problem;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemType;
import com.moplus.moplus_server.admin.problem.dto.request.ProblemPostRequest;
import com.moplus.moplus_server.admin.problem.dto.response.ProblemPostResponse;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
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
@Sql({"/practice-test-tag.sql", "/concept-tag.sql"})
@SpringBootTest
class ProblemSaveServiceTest {

    @Autowired
    private ProblemSaveService problemSaveService;

    @Autowired
    private ProblemRepository problemRepository;

    private ProblemPostRequest problemPostRequest;

    @BeforeEach
    void setUp() {
        problemPostRequest = new ProblemPostRequest(
                ProblemType.GICHUL_PROBLEM,
                1L,
                20
        );
    }

    @Nested
    class 문항생성 {

        @Test
        void 성공() {
            // when
            ProblemPostResponse problemResponse = problemSaveService.createProblem(problemPostRequest);

            // then
            assertThat(problemResponse).isNotNull();

            Problem savedProblem = problemRepository.findByIdElseThrow(problemResponse.id());
            assertThat(savedProblem).isNotNull();
            assertThat(savedProblem.getProblemType()).isEqualTo(ProblemType.GICHUL_PROBLEM);
        }

        @Test
        void 문제_저장_실패_잘못된_부모_문제_ID() {
            // given
            ProblemPostRequest invalidParentRequest = new ProblemPostRequest(
                    ProblemType.GICHUL_PROBLEM,
                    999L, // 존재하지 않는 parentId 사용
                    10
            );

            // when & then
            assertThatThrownBy(() -> problemSaveService.createProblem(invalidParentRequest))
                    .isInstanceOf(NotFoundException.class);
        }
    }

}