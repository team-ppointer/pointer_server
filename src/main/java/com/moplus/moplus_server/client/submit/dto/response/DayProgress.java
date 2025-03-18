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
        else if (problemStatuses.contains(ProblemSubmitStatus.IN_PROGRESS)) {
            return IN_PROGRESS;
        }
        else{
            return COMPLETE;
        }
    }
}
