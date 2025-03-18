package com.moplus.moplus_server.client.submit.domain;

public enum ProblemSubmitStatus {
    CORRECT,
    INCORRECT,
    IN_PROGRESS,
    RETRY_CORRECT;
    public static ProblemSubmitStatus determineStatus(ProblemSubmitStatus currentStatus, String memberAnswer, String problemAnswer) {
        boolean isCorrect = problemAnswer.trim().equals(memberAnswer.trim());

        if (currentStatus == CORRECT) {
            return isCorrect ? CORRECT : INCORRECT;
        } else if (currentStatus == INCORRECT) {
            return isCorrect ? RETRY_CORRECT : INCORRECT;
        } else if (currentStatus == IN_PROGRESS) {
            return isCorrect ? CORRECT : INCORRECT;
        } else {
            return isCorrect ? RETRY_CORRECT : INCORRECT;
        }
    }
}
