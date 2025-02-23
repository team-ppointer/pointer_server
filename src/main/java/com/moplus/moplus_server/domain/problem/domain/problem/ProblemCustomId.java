package com.moplus.moplus_server.domain.problem.domain.problem;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemCustomId implements Serializable {

    @Column(name = "problem_custom_id")
    private String id;

    public ProblemCustomId(String id) {
        this.id = id;
    }
}
