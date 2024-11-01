package com.moplus.moplus_server.domain.practiceTest.domain;


import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Subject {

//    화법과작문("화법과작문", 45, 100),
//    언어와매체("언어와매체", 45, 100),
    미적분("미적분", 30, 100),
    확률과통계("확률과통계",30, 100),
    기하("기하",30, 100);
//    영어("영어",45, 100),
//    물리I("물리I",20, 50),
//    화학I("화학I",20, 50),
//    생명과학I("생명과학I",20, 50),
//    지구과학I("지구과학I",20, 50),
//    물리II("물리II",20, 50),
//    화학II("화학II",20, 50),
//    생명과학II("생명과학II",20, 50),
//    지구과학II("지구과학II",20, 50),
//    한국지리("한국지리",20, 50),
//    세계지리("세계지리",20, 50),
//    동아시아사("동아시아사",20, 50),
//    생활과윤리("생활과윤리",20, 50),
//    윤리와사상("윤리와사상",20, 50),
//    사회문화("사회문화",20, 50),
//    정치와법("정치와법",20, 50),
//    경제("경제",20, 50);

    private final String value;
    private final int problemCount;
    private final int perfectScore;

    public static Subject fromValue(String value) {
        return Arrays.stream(Subject.values())
            .filter(subject -> subject.value.equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 값에 맞는 Subject가 없습니다: " + value));
    }
}
