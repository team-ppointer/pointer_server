package com.moplus.moplus_server.domain.v0.practiceTest.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockPracticeTestFacade {

    private final PracticeTestService practiceTestService;

    public void updateViewCount(Long id) throws InterruptedException {
        while (true) {
            try {
                practiceTestService.updateViewCount(id);

                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }

    }
}
