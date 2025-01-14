package com.moplus.moplus_server.domain.practiceTest.service.client;

import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.domain.Problem;
import com.moplus.moplus_server.domain.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.domain.practiceTest.repository.ProblemRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("h2test")
public class ProblemServiceConcurrencyTest {

    @Autowired
    private PracticeTestService practiceTestService;

    @Autowired
    private PracticeTestRepository practiceTestRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemService problemService;

    @BeforeEach
    void setup() {
        PracticeTest practiceTest = new PracticeTest();
        practiceTestRepository.save(practiceTest);
        PracticeTest entity = practiceTestRepository.findById(practiceTest.getId()).orElseThrow();

        Problem problem = Problem.builder()
                .problemNumber("1")
                .answer("42")
                .point(5)
                .incorrectNum(10L)
                .practiceTest(entity) // Assume we have a PracticeTest entity linked here
                .correctRate(0.5)
                .build();
        problemRepository.save(problem);
    }

    @Test
    public void testConcurrentUpdateCorrectRateAndCount() throws InterruptedException {
        Long practiceTestId = 1L;
        Long problemId = 1L;
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(36);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        Problem problem = problemRepository.findById(1L).orElseThrow();

        for (int i = 0; i < threadCount; i++) {
            if (i % 2 == 0) {
                executorService.submit(() -> {
                    try {
                        practiceTestService.updateViewCount(practiceTestId);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            } else {
                executorService.submit(() -> {
                    try {
                        problemService.updateCorrectRate(practiceTestId, "1", 0.7);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
        }
        countDownLatch.await();

        PracticeTest practiceTest = practiceTestRepository.findById(practiceTestId).orElseThrow();

        Assertions.assertEquals(threadCount / 2, practiceTest.getViewCount());
    }

}
