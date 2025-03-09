package com.moplus.moplus_server.client.submit.domain;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemSubmit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_submit_problem_id")
    private Long id;

    @Column(name = "set_submit_id", nullable = false)
    private Long setSubmitId;

    @Column(name = "problem_id", nullable = false)
    private Long problemId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmitStatus status;

    @Builder
    public ProblemSubmit(Long setSubmitId, Long problemId, SubmitStatus status) {
        this.setSubmitId = setSubmitId;
        this.problemId = problemId;
        this.status = status;
    }
}
