package com.moplus.moplus_server.statistic.member;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CorrectConceptTagStatistics extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "correct_concept_tag_statistics_id")
    private Long id;

    @Column(name = "concept_tag_id", nullable = false)
    private Long conceptTagId;

    private int correctCount;

    @Builder
    public CorrectConceptTagStatistics(Long conceptTagId, int correctCount) {
        this.conceptTagId = conceptTagId;
        this.correctCount = correctCount;
    }

    public void incrementCorrectCount() {
        this.correctCount++;
    }
}