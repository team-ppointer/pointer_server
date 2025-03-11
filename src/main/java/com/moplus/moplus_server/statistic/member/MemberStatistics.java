package com.moplus.moplus_server.statistic.member;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberStatistics extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_statistics_id")
    private Long id;

    @Column(name = "member_id", nullable = false, unique = true)
    private Long memberId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_statistics_id")
    private List<CorrectConceptTagStatistics> correctStatistics = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_statistics_id")
    private List<IncorrectConceptTagStatistics> incorrectStatistics = new ArrayList<>();

    @Builder
    public MemberStatistics(Long memberId) {
        this.memberId = memberId;
    }

    public void addCorrectStatistics(CorrectConceptTagStatistics correctStatistics) {
        this.correctStatistics.add(correctStatistics);
    }

    public void addIncorrectStatistics(IncorrectConceptTagStatistics incorrectStatistics) {
        this.incorrectStatistics.add(incorrectStatistics);
    }
}