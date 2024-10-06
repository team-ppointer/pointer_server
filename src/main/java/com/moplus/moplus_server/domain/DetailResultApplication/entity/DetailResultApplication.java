package com.moplus.moplus_server.domain.DetailResultApplication.entity;

import com.moplus.moplus_server.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class DetailResultApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long testResultId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Builder
    public DetailResultApplication(Long testResultId, String name, String phoneNumber) {
        this.testResultId = testResultId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
