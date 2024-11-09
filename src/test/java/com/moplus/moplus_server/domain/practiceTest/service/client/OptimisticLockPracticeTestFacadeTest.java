package com.moplus.moplus_server.domain.practiceTest.service.client;

import static org.junit.jupiter.api.Assertions.*;

import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.repository.PracticeTestRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class OptimisticLockPracticeTestFacadeTest {
    @Autowired
    private OptimisticLockPracticeTestFacade optimisticLockPracticeTestFacade;

    @Autowired
    private PracticeTestRepository practiceTestRepository;

    @BeforeEach
    void setup() {
        PracticeTest practiceTest = new PracticeTest();
        practiceTestRepository.save(practiceTest);
    }

    @Test
    public void 동시에_조회수가_정상적으로_업데이트_되어야한다() throws InterruptedException {
        Long practiceTestId = 1L;
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(36);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    optimisticLockPracticeTestFacade.updateViewCount(practiceTestId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        PracticeTest practiceTest = practiceTestRepository.findById(practiceTestId).orElseThrow();
        assertEquals(threadCount, practiceTest.getViewCount());
    }
}