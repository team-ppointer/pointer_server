package com.moplus.moplus_server.client.submit.dto.response;

import com.moplus.moplus_server.client.submit.domain.ProblemSubmitStatus;
import java.util.List;

public enum DayProgress {
    COMPLETE,
    INCOMPLETE,
    IN_PROGRESS;
    public static DayProgress determineDayProgress(List<ProblemSubmitStatus> problemStatuses) {
        if (problemStatuses.isEmpty()) {
            return INCOMPLETE;
        }
        boolean allNotStarted = problemStatuses.stream()
                .allMatch(status -> status == ProblemSubmitStatus.NOT_STARTED);

        boolean allFinished = problemStatuses.stream()
                .allMatch(status -> status == ProblemSubmitStatus.CORRECT || status == ProblemSubmitStatus.INCORRECT || status == ProblemSubmitStatus.RETRY_CORRECT);

        if (allNotStarted) {
            return INCOMPLETE;
        } else if (allFinished) {
            return COMPLETE;
        } else {
            return IN_PROGRESS;
        }
    }
}
