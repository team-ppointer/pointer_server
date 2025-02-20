package com.moplus.moplus_server.domain.problem.domain.problem;

import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.InvalidValueException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedTime {
    @Column(name = "recommended_minute")
    private Integer minute;
    @Column(name = "recommended_second")
    private Integer second;

    public RecommendedTime(Integer minute, Integer second) {
        validateTime(minute, second);
        this.minute = minute != null ? minute : 0;
        this.second = second != null ? second : 0;
    }

    private void validateTime(Integer minute, Integer second) {
        if (minute != null && (minute < 0 || minute > 60)) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (second != null && (second < 0 || second > 60)) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}