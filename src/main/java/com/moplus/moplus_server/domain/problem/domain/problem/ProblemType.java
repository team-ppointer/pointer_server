package com.moplus.moplus_server.domain.problem.domain.problem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProblemType {
    MULTIPLE_CHOICE("객관식"),
    SHORT_NUMBER_ANSWER("주관식_숫자"),
    SHORT_STRING_ANSWER("주관식_문자");


    private final String name;

    public static ProblemType getTypeForProblem(String subject, int number) {

        // 미적분, 기하, 확률과 통계
        if (subject.equals("미적분") || subject.equals("기하") || subject.equals("확률과통계")) {
            if ((number >= 1 && number <= 15) || (number >= 23 && number <= 28)) {
                return MULTIPLE_CHOICE;
            } else if ((number >= 16 && number <= 22) || number == 29 || number == 30) {
                return SHORT_NUMBER_ANSWER;
            }
        }

        // 고1, 고2
        if (subject.equals("고1") || subject.equals("고2")) {
            if (number >= 1 && number <= 21) {
                return MULTIPLE_CHOICE;
            } else if (number >= 22 && number <= 30) {
                return SHORT_NUMBER_ANSWER;
            }
        }

        // 기본값: 객관식
        return MULTIPLE_CHOICE;
    }
}
