package com.moplus.moplus_server.global.scheduler;

import com.moplus.moplus_server.domain.TestResult.service.TestResultService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestResultScheduler {

    private final TestResultService testResultService;

//    // 5분마다 실행 (cron 표현식을 사용해 5분마다 스케줄링)
//    @Scheduled(cron = "0 */5 * * * *")
//    public void calculateAverageSolvingTime() {
//        Duration averageDuration = testResultService.calculateAverageSolvingTime();
//    }
}
