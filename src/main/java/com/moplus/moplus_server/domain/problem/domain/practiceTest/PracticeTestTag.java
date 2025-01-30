package com.moplus.moplus_server.domain.problem.domain.practiceTest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "practice_test_tag")
@NoArgsConstructor
public class PracticeTestTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "test_year")
    private int year;
    @Column(name = "test_month")
    private int month;
    @Enumerated(value = EnumType.STRING)
    private Subject subject;
    private String area;

    public PracticeTestTag(String name, int year, int month, Subject subject) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.subject = subject;
        this.area = "수학";
    }
}
