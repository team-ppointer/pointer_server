package com.moplus.moplus_server.domain.problem.domain.problem;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class ProblemId implements Serializable {

    @Column(name = "problem_id")
    private String id;

    public ProblemId(String id) {
        this.id = id;
    }
}
