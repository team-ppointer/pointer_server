package com.moplus.moplus_server.domain.v0.practiceTest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProblemImageForTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_image_for_test_id")
    private Long id;

    private String fileName;

    private String imageUrl;

    private Long problemId;

    @Builder
    public ProblemImageForTest(String fileName, String imageUrl, Long problemId) {
        this.fileName = fileName;
        this.imageUrl = imageUrl;
        this.problemId = problemId;
    }
}
