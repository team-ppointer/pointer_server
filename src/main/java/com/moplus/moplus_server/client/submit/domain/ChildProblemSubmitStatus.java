package com.moplus.moplus_server.client.submit.domain;

public enum ChildProblemSubmitStatus {
    CORRECT,
    INCORRECT,
    RETRY_CORRECT,
    NOT_STARTED;
    public static ChildProblemSubmitStatus determineStatus(ChildProblemSubmitStatus currentStatus, String memberAnswer, String childProblemAnswer) {
        boolean isCorrect = childProblemAnswer.trim().equals(memberAnswer.trim());

        if (currentStatus == CORRECT) {
            return isCorrect ? CORRECT : INCORRECT;
        } else if (currentStatus == INCORRECT) {
            return isCorrect ? RETRY_CORRECT : INCORRECT;
        } else if (currentStatus == RETRY_CORRECT) {
            return isCorrect ? RETRY_CORRECT : INCORRECT;
        } else {
            return isCorrect ? CORRECT : INCORRECT;
        }
    }
}
