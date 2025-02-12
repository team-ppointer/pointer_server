package com.moplus.moplus_server.domain.problemset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.moplus.moplus_server.domain.problemset.domain.ProblemSet;
import com.moplus.moplus_server.domain.problemset.domain.ProblemSetConfirmStatus;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemReorderRequest;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemSetPostRequest;
import com.moplus.moplus_server.domain.problemset.dto.request.ProblemSetUpdateRequest;
import com.moplus.moplus_server.domain.problemset.repository.ProblemSetRepository;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetSaveService;
import com.moplus.moplus_server.domain.problemset.service.ProblemSetUpdateService;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("h2test")
@Sql({"/insert-problem2.sql"})
@SpringBootTest
public class ProblemSetServiceTest {

    @Autowired
    private ProblemSetSaveService problemSetSaveService;

    @Autowired
    private ProblemSetUpdateService problemSetUpdateService;

    @Autowired
    private ProblemSetRepository problemSetRepository;

    private ProblemSetPostRequest problemSetPostRequest;

    @BeforeEach
    void setUp() {
        // 초기 문항 세트 생성 요청 데이터 준비
        problemSetPostRequest = new ProblemSetPostRequest(
                "초기 문항세트",
                List.of(1L, 2L, 3L)
        );
    }

    @Test
    void 문항세트_생성_테스트() {
        // when
        Long problemSetId = problemSetSaveService.createProblemSet(problemSetPostRequest);

        // then
        ProblemSet savedProblemSet = problemSetRepository.findById(problemSetId)
                .orElseThrow(() -> new IllegalArgumentException("문항세트를 찾을 수 없습니다."));

        assertThat(savedProblemSet).isNotNull();
        assertThat(savedProblemSet.getTitle().getValue()).isEqualTo("초기 문항세트");
        assertThat(savedProblemSet.getProblemIds()).hasSize(3);
    }

    @Test
    void 문항세트_문항순서_변경_테스트() {
        // given
        Long problemSetId = problemSetSaveService.createProblemSet(problemSetPostRequest);

        // when
        ProblemReorderRequest reorderRequest = new ProblemReorderRequest(
                List.of(1L, 2L, 3L)
        );
        problemSetUpdateService.reorderProblems(problemSetId, reorderRequest);

        // then
        ProblemSet updatedProblemSet = problemSetRepository.findById(problemSetId)
                .orElseThrow(() -> new IllegalArgumentException("문항세트를 찾을 수 없습니다."));

    }

    @Test
    void 문항세트_업데이트_테스트() {
        // given
        Long problemSetId = problemSetSaveService.createProblemSet(problemSetPostRequest);

        // when
        ProblemSetUpdateRequest updateRequest = new ProblemSetUpdateRequest(
                "업데이트된 문항세트",
                List.of(1L, 2L)
        );
        problemSetUpdateService.updateProblemSet(problemSetId, updateRequest);

        // then
        ProblemSet updatedProblemSet = problemSetRepository.findByIdElseThrow(problemSetId);

        assertThat(updatedProblemSet.getTitle().getValue()).isEqualTo("업데이트된 문항세트");
        assertThat(updatedProblemSet.getProblemIds()).hasSize(2);

    }

    @Test
    void 문항세트_컨펌_토글_테스트() {
        // given
        Long problemSetId = problemSetSaveService.createProblemSet(problemSetPostRequest);

        // when
        ProblemSetConfirmStatus firstToggleStatus = problemSetUpdateService.toggleConfirmProblemSet(
                problemSetId); // CONFIRMED
        ProblemSetConfirmStatus secondToggleStatus = problemSetUpdateService.toggleConfirmProblemSet(
                problemSetId); // NOT_CONFIRMED

        // then
        assertThat(firstToggleStatus).isEqualTo(ProblemSetConfirmStatus.CONFIRMED); // 첫 번째 호출 후 컨펌 상태 확인
        assertThat(secondToggleStatus).isEqualTo(ProblemSetConfirmStatus.NOT_CONFIRMED); // 두 번째 호출 후 비컨펌 상태 확인
    }

    @Test
    void 유효하지_않은_문항이_포함된_문항세트_컨펌_실패_테스트() {
        // given
        Long problemSetId = problemSetSaveService.createProblemSet(problemSetPostRequest);

        // 유효하지 않은 문항을 포함하도록 설정 (문항 ID가 존재하지 않거나 필수 필드가 누락된 경우)
        ProblemSetUpdateRequest invalidUpdateRequest = new ProblemSetUpdateRequest(
                "유효하지 않은 문항세트",
                List.of(1L, 4L)
        );
        problemSetUpdateService.updateProblemSet(problemSetId, invalidUpdateRequest);

        // when & then
        assertThatThrownBy(() -> problemSetUpdateService.toggleConfirmProblemSet(problemSetId))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("24052001004번") // 메시지에 포함된 ID 확인
                .hasMessageContaining(ErrorCode.INVALID_CONFIRM_PROBLEM.getMessage());
    }

    @Test
    void 빈_문항리스트_문항세트_생성_실패_테스트() {
        // given
        ProblemSetPostRequest emptyProblemSetRequest = new ProblemSetPostRequest(
                "빈 문항세트",
                List.of() // 빈 리스트
        );

        // when & then
        assertThatThrownBy(() -> problemSetSaveService.createProblemSet(emptyProblemSetRequest))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining(ErrorCode.EMPTY_PROBLEMS_ERROR.getMessage());
    }

    @Test
    void 빈_제목_문항세트_생성_테스트() {
        // given
        ProblemSetPostRequest emptyTitleRequest = new ProblemSetPostRequest(
                "", // 빈 문자열 제목
                List.of(1L, 2L, 3L)
        );

        ProblemSetPostRequest nullTitleRequest = new ProblemSetPostRequest(
                null, // null 제목
                List.of(1L, 2L, 3L)
        );

        // when
        Long emptyTitleProblemSetId = problemSetSaveService.createProblemSet(emptyTitleRequest);
        Long nullTitleProblemSetId = problemSetSaveService.createProblemSet(nullTitleRequest);

        // then
        ProblemSet emptyTitleSavedProblemSet = problemSetRepository.findByIdElseThrow(emptyTitleProblemSetId);

        ProblemSet nullTitleSavedProblemSet = problemSetRepository.findByIdElseThrow(nullTitleProblemSetId);

        assertThat(emptyTitleSavedProblemSet.getTitle().getValue()).isEqualTo("제목 없음"); // 빈 문자열 제목 테스트
        assertThat(nullTitleSavedProblemSet.getTitle().getValue()).isEqualTo("제목 없음"); // null 제목 테스트
    }

    @Test
    void 문항세트_빈_제목_업데이트_테스트() {
        // given
        Long problemSetId = problemSetSaveService.createProblemSet(problemSetPostRequest);

        ProblemSetUpdateRequest emptyUpdateRequest = new ProblemSetUpdateRequest(
                "업데이트된 빈 문항세트",
                List.of()
        );

        // when & then
        assertThatThrownBy(() -> problemSetUpdateService.updateProblemSet(problemSetId, emptyUpdateRequest))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining(ErrorCode.EMPTY_PROBLEMS_ERROR.getMessage());
    }
}