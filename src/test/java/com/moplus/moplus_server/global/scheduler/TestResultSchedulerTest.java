package com.moplus.moplus_server.global.scheduler;

import static org.mockito.Mockito.when;

import com.moplus.moplus_server.domain.problem.domain.practiceTest.Subject;
import com.moplus.moplus_server.domain.v0.TestResult.entity.TestResult;
import com.moplus.moplus_server.domain.v0.TestResult.repository.TestResultRepository;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.PracticeTestRepository;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TestResultSchedulerTest {

    @Mock
    private PracticeTestRepository practiceTestRepository;

    @Mock
    private TestResultRepository testResultRepository;

    @InjectMocks
    private TestResultScheduler testResultScheduler;  // calculateAverageSolvingTime 메서드를 가진 클래스

    private PracticeTest practiceTest;
    private TestResult testResult1;
    private TestResult testResult2;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // PracticeTest 객체 초기화
        practiceTest = PracticeTest.builder()
                .name("Sample Test")
                .round("1st Round")
                .provider("Provider A")
                .publicationYear("2024")
                .subject(Subject.미적분)
                .build();
        Field idField = PracticeTest.class.getDeclaredField("id");
        idField.setAccessible(true); // private 필드 접근 허용
        idField.set(practiceTest, 1L); //

        // TestResult 객체 초기화 (각 테스트의 풀이 시간을 설정)
        testResult1 = TestResult.builder()
                .score(85)
                .solvingTime(Duration.ofMinutes(30))  // 30분 걸림
                .practiceTestId(practiceTest.getId())
                .build();
        practiceTest.plus1SolvesCount();

        testResult2 = TestResult.builder()
                .score(90)
                .solvingTime(Duration.ofMinutes(45))  // 45분 걸림
                .practiceTestId(practiceTest.getId())
                .build();
        practiceTest.plus1SolvesCount();
    }

    @Test
    void 평균시간계산() {

        when(practiceTestRepository.findAll()).thenReturn(List.of(practiceTest));
        List<TestResult> testResultsForPracticeTest1 = List.of(testResult1, testResult2);
        when(testResultRepository.findAllByPracticeTestId(1L)).thenReturn(testResultsForPracticeTest1);

        // 메서드 실행
        testResultScheduler.calculateAverageSolvingTime();

        // 검증
        long totalSeconds = testResult1.getSolvingTime().getSeconds() + testResult2.getSolvingTime().getSeconds();

        long averageSeconds = totalSeconds / 2;

        Duration expectedAverage = Duration.ofSeconds(averageSeconds);
        System.out.println(expectedAverage);

        Assertions.assertEquals(practiceTest.getAverageSolvingTime(), expectedAverage);

    }
}