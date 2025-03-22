package com.moplus.moplus_server.client.submit.domain;

public enum ProblemSubmitStatus {
    CORRECT,
    INCORRECT,
    IN_PROGRESS,
    RETRY_CORRECT;
    public static ProblemSubmitStatus determineStatus(ProblemSubmitStatus currentStatus, String memberAnswer, String problemAnswer) {
        boolean isCorrect = problemAnswer.trim().equals(memberAnswer.trim());

        return switch (currentStatus) {
            case CORRECT -> isCorrect ? CORRECT : INCORRECT;
            case INCORRECT -> isCorrect ? RETRY_CORRECT : INCORRECT;
            case IN_PROGRESS -> isCorrect ? CORRECT : INCORRECT;
            default -> isCorrect ? RETRY_CORRECT : INCORRECT;
        };
    }
}
