package com.moplus.moplus_server.domain.problem.domain.problem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.PracticeTestTag;
import com.moplus.moplus_server.domain.problem.domain.practiceTest.Subject;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProblemIdServiceTest {

    @Mock
    private ProblemRepository problemRepository;

    @InjectMocks
    private ProblemIdService problemIdService;

    private PracticeTestTag practiceTestTag;

    @BeforeEach
    void setUp() {
        practiceTestTag = Mockito.mock(PracticeTestTag.class);
        when(practiceTestTag.getSubject()).thenReturn(Subject.고2);
        when(practiceTestTag.getYear()).thenReturn(2024);
        when(practiceTestTag.getMonth()).thenReturn(5);
    }

    @Test
    void nextId_정상생성_및_중복확인() {
        // given
        int 문제번호 = 20;
        when(problemRepository.existsById(any(ProblemId.class))).thenReturn(false); // 중복 없음

        // when
        ProblemId generatedId = problemIdService.nextId(문제번호, practiceTestTag);

        // then
        assertThat(generatedId).isNotNull();
        assertThat(generatedId.getId()).matches("\\d{13}"); // ID 형식이 맞는지 확인
        assertThat(generatedId.getId()).startsWith("2405200120");

        // 문제 ID 중복 확인을 위해 existsById 호출 확인
        verify(problemRepository, atLeastOnce()).existsById(any(ProblemId.class));

    }

    @Test
    void nextId_중복발생시_다시_생성() {
        // given
        int 문제번호 = 2;
        when(problemRepository.existsById(any(ProblemId.class)))
                .thenReturn(true)  // 첫 번째 생성된 ID는 중복됨
                .thenReturn(false); // 두 번째는 중복 없음

        // when
        ProblemId generatedId = problemIdService.nextId(문제번호, practiceTestTag);

        // then
        assertThat(generatedId).isNotNull();
        assertThat(generatedId.getId()).matches("\\d{13}");
        assertThat(generatedId.getId()).startsWith("2405020120");

        // 중복된 ID가 나왔으므로 existsById가 최소 두 번 이상 호출되었는지 확인
        verify(problemRepository, atLeast(2)).existsById(any(ProblemId.class));
    }
}