package com.moplus.moplus_server.global.scheduler;

import com.moplus.moplus_server.domain.v0.TestResult.entity.TestResult;
import com.moplus.moplus_server.domain.v0.TestResult.repository.TestResultRepository;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.PracticeTestRepository;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestResultScheduler {

    private final PracticeTestRepository practiceTestRepository;
    private final TestResultRepository testResultRepository;


    // 5분마다 실행 (cron 표현식을 사용해 5분마다 스케줄링)
    @Scheduled(cron = "0 */5 * * * *")
    public void calculateAverageSolvingTime() {
        List<PracticeTest> practiceTests = practiceTestRepository.findAll();

        for (PracticeTest practiceTest : practiceTests) {
            if (practiceTest.getSolvesCount() == 0) {
                continue;
            }

            Duration sum = Duration.ZERO;
            List<TestResult> allByPracticeTestId =
                    testResultRepository.findAllByPracticeTestId(practiceTest.getId());

            long validCount = 0;

            for (TestResult testResult : allByPracticeTestId) {
                Duration solvingTime = testResult.getSolvingTime();

                // solvingTime이 null이거나 0초일 경우는 제외
                if (solvingTime != null && !solvingTime.isZero()) {
                    sum = sum.plus(solvingTime);  // Duration 객체는 불변이므로 새로운 객체로 할당
                    validCount++;  // 유효한 solvingTime이 있을 때만 카운트 증가
                }
            }

            if (validCount > 0) {
                // 유효한 solvingTime이 있는 경우만 평균 계산
                Duration average = sum.dividedBy(validCount);

                // 초 단위까지 포함한 average를 저장
                practiceTest.updateAverageSolvingTime(average);
                practiceTestRepository.save(practiceTest);
            }
            System.out.println(
                    "평균 풀이 시간 계산 완료 : " + practiceTest.getId() + "L, 평균 시간 " + practiceTest.getAverageSolvingTime());
        }
    }
}
