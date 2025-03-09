package com.moplus.moplus_server.admin.publish.domain;

import com.moplus.moplus_server.global.common.BaseEntity;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Publish extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publish_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate publishedDate;

    @Column(name = "problem_set_id", nullable = false)
    private Long problemSetId;

    @Builder
    public Publish(LocalDate publishedDate, Long problemSetId) {
        this.publishedDate = publishedDate;
        this.problemSetId = problemSetId;
    }

    public void validatePublishedDate() {
        // 발행 시점 다음날부터 발행 가능
        if (this.publishedDate.isBefore(LocalDate.now())) {
            throw new InvalidValueException(ErrorCode.INVALID_DATE_ERROR);
        }
    }

    public void validateDeletable() {
        if (this.publishedDate.isBefore(LocalDate.now())) {
            throw new InvalidValueException(ErrorCode.CANNOT_DELETE_PAST_PUBLISH);
        }
    }
}
