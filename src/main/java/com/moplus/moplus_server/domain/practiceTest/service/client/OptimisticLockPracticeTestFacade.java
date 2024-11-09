package com.moplus.moplus_server.domain.practiceTest.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockPracticeTestFacade {

    private final PracticeTestService practiceTestService;

    public void updateViewCount(Long id) throws InterruptedException {
        int retryCount = 0;
        int maxRetries = 5;

        while (retryCount < maxRetries) {
            try {
                practiceTestService.updateViewCount(id);
                break;
            } catch (Exception e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    throw new RuntimeException("최대 재시도 횟수를 초과했습니다.", e);
                }
                Thread.sleep(50);
            }
        }
    }
}
