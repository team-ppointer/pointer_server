package com.moplus.moplus_server.statistic.Problem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChildProblemStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_problem_statistic_id")
    private Long id;

    private Long childProblemId;

    @Embedded
    private CountStatistic countStatistic;

    public ChildProblemStatistic(Long childProblemId) {
        this.childProblemId = childProblemId;
        this.countStatistic = new CountStatistic();
    }
}
