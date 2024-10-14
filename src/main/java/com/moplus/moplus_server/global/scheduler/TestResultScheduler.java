package com.moplus.moplus_server.global.scheduler;

import com.moplus.moplus_server.domain.TestResult.entity.TestResult;
import com.moplus.moplus_server.domain.TestResult.repository.TestResultRepository;
import com.moplus.moplus_server.domain.TestResult.service.TestResultService;
import com.moplus.moplus_server.domain.practiceTest.entity.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.repository.PracticeTestRepository;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Long> practiceIdList = practiceTests.stream()
            .map(PracticeTest::getId)
            .toList();


        for (int i = 0; i < practiceIdList.size(); i++) {
            // 모든 solvingTime을 더해서 평균을 내고 싶어
            PracticeTest practiceTest = practiceTests.get(i);
            if (practiceTest.getSolvesCount() == 0) {
                continue;
            }
            Duration sum = Duration.ZERO;
            List<TestResult> allByPracticeTestId =
                testResultRepository.findAllByPracticeTestId(practiceIdList.get(i));
            for (TestResult testResult : allByPracticeTestId) {
                Duration solvingTime = testResult.getSolvingTime();
                sum = sum.plus(solvingTime);  // Duration 객체는 불변이므로 새로운 객체로 할당
            }
            // 평균 계산
            if (!allByPracticeTestId.isEmpty()) {
                Duration average = sum.dividedBy(allByPracticeTestId.size());
                practiceTest.updateAverageSolvingTime(average);
                practiceTestRepository.save(practiceTest);
            }
        }
    }
}
