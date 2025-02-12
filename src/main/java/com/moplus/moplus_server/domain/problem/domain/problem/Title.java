package com.moplus.moplus_server.domain.problem.domain.problem;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Title {

    private static final String DEFAULT_TITLE = "제목 없음";

    @Column(name = "title")
    private String title;

    public Title(String title) {
        this.title = verifyTitle(title);
    }

    private static String verifyTitle(String title) {
        return (title == null || title.trim().isEmpty()) ? DEFAULT_TITLE : title;
    }
}
