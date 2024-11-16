package com.moplus.moplus_server.domain.practiceTest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProblemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_image_id")
    private Long id;

    private String fileName;

    private String imageUrl;

    private Long problemId;

    @Builder
    public ProblemImage(String fileName, String imageUrl, Long problemId) {
        this.fileName = fileName;
        this.imageUrl = imageUrl;
        this.problemId = problemId;
    }
}
