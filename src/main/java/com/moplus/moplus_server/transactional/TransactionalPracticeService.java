package com.moplus.moplus_server.transactional;

import com.moplus.moplus_server.domain.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.practiceTest.service.client.PracticeTestService;

public class TransactionalPracticeService {

    private final PracticeTestService practiceTestService;

    public TransactionalPracticeService(PracticeTestService practiceTestService) {
        this.practiceTestService = practiceTestService;
    }

    public void updateViewCount(Long practiceTestId) {
        startTransaction();

        practiceTestService.updateViewCount(practiceTestId);

        endTransaction();
    }

    private void startTransaction() {
        // start transaction
    }

    private void endTransaction() {
        // end transaction
    }
}
