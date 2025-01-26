package com.moplus.moplus_server.domain.v0.practiceTest.domain;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnswerFormat {

    객관식("객관식"),
    단답식("단답식");

    private final String value;

    public static AnswerFormat fromValue(String value) {
        return Arrays.stream(AnswerFormat.values())
                .filter(answerFormat -> answerFormat.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 값에 맞는 answerFormat가 없습니다: " + value));
    }
}
