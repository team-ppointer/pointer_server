package com.moplus.moplus_server.domain.problemset.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Title {

    private static final String DEFAULT_TITLE = "제목 없음";

    @Column(name = "title", nullable = false)
    private String value;

    public Title(String title) {
        this.value = verifyTitle(title);
    }

    private static String verifyTitle(String title) {
        return (title == null || title.trim().isEmpty()) ? DEFAULT_TITLE : title;
    }
}
