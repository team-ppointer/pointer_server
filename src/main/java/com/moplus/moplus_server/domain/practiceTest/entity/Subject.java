package com.moplus.moplus_server.domain.practiceTest.entity;


import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Subject {

    화법과작문("화법과작문", 45),
    언어와매체("언어와매체", 45),
    미적분("미적분", 30),
    확률과통계("확률과통계",30),
    기하("기하",30),
    영어("영어",45),
    물리I("물리I",20),
    화학I("화학I",20),
    생명과학I("생명과학I",20),
    지구과학I("지구과학I",20),
    물리II("물리II",20),
    화학II("화학II",20),
    생명과학II("생명과학II",20),
    지구과학II("지구과학II",20),
    한국지리("한국지리",20),
    세계지리("세계지리",20),
    동아시아사("동아시아사",20),
    생활과윤리("생활과윤리",20),
    윤리와사상("윤리와사상",20),
    사회문화("사회문화",20),
    정치와법("정치와법",20),
    경제("경제",20);

    private final String value;
    private final int problemCount;

    public static Subject fromValue(String value) {
        return Arrays.stream(Subject.values())
            .filter(subject -> subject.value.equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 값에 맞는 Subject가 없습니다: " + value));
    }
}
