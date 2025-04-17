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
    고3_공통("고3_공통", 30, 100, 6),
    가형("가형", 30, 100, 7),
    나형("나형", 30, 100, 8),
    ;

    private final String value;
    private final int problemCount;
    private final int perfectScore;
    private final int code;

    public static Subject fromValue(String value) {
        return Arrays.stream(Subject.values())
                .filter(subject -> subject.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 값에 맞는 Subject가 없습니다: " + value));
    }
}
