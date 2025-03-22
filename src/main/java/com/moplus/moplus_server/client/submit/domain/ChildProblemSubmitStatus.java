package com.moplus.moplus_server.client.submit.domain;

public enum ChildProblemSubmitStatus {
    CORRECT,
    INCORRECT,
    RETRY_CORRECT,
    NOT_STARTED;
    public static ChildProblemSubmitStatus determineStatus(ChildProblemSubmitStatus currentStatus, String memberAnswer, String childProblemAnswer) {
        boolean isCorrect = childProblemAnswer.trim().equals(memberAnswer.trim());

        return switch (currentStatus) {
            case CORRECT -> isCorrect ? CORRECT : INCORRECT;
            case INCORRECT -> isCorrect ? RETRY_CORRECT : INCORRECT;
            case RETRY_CORRECT -> isCorrect ? RETRY_CORRECT : INCORRECT;
            default -> isCorrect ? CORRECT : INCORRECT;
        };
    }
}
