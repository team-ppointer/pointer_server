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
public class ChildProblemSubmit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_problem_submit_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "publish_id", nullable = false)
    private Long publishId;

    @Column(name = "child_problem_id", nullable = false)
    private Long childProblemId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChildProblemSubmitStatus status;

    @Builder
    public ChildProblemSubmit(Long memberId, Long publishId, Long childProblemId, ChildProblemSubmitStatus status) {
        this.memberId = memberId;
        this.publishId = publishId;
        this.childProblemId = childProblemId;
        this.status = status;
    }

    public void updateStatus(ChildProblemSubmitStatus status) {
        this.status = status;
    }
}
