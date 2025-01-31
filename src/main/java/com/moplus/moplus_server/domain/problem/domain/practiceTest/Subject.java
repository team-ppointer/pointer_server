package com.moplus.moplus_server.domain.problem.domain.practiceTest;


import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Subject {

    고1("고1", 30, 100, 1),
    고2("고2", 30, 100, 2),
    미적분("미적분", 30, 100, 3),
    기하("기하", 30, 100, 4),
    확률과통계("확률과통계", 30, 100, 5),
    ;

    private final String value;
    private final int problemCount;
    private final int perfectScore;
    private final int idCode;

    public static Subject fromValue(String value) {
        return Arrays.stream(Subject.values())
                .filter(subject -> subject.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 값에 맞는 Subject가 없습니다: " + value));
    }
}
