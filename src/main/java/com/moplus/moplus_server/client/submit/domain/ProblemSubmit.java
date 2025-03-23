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
    @Column(name = "problem_submit_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "publish_id", nullable = false)
    private Long publishId;

    @Column(name = "problem_id", nullable = false)
    private Long problemId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemSubmitStatus status;

    @Builder
    public ProblemSubmit(Long memberId, Long publishId, Long problemId, ProblemSubmitStatus status) {
        this.memberId = memberId;
        this.publishId = publishId;
        this.problemId = problemId;
        this.status = status;
    }

    public void updateStatus(ProblemSubmitStatus status) {
        this.status = status;
    }
}
