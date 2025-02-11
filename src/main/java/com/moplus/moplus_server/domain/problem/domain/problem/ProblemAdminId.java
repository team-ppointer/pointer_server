package com.moplus.moplus_server.domain.problem.domain.problem;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class ProblemAdminId implements Serializable {

    @Column(name = "problem_admin_id")
    private String id;

    public ProblemAdminId(String id) {
        this.id = id;
    }
}
