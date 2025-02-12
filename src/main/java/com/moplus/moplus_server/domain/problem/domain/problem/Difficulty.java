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
public class Difficulty {

    @Column(name = "difficulty")
    private Integer difficulty;

    public Difficulty(Integer difficulty) {
        if (difficulty == null) {
            return;
        }
        validate(difficulty);
        this.difficulty = difficulty;
    }

    private void validate(int difficulty) {
        if (difficulty < 1 || difficulty > 10) {
            throw new InvalidValueException(ErrorCode.INVALID_DIFFICULTY);
        }
    }
}
