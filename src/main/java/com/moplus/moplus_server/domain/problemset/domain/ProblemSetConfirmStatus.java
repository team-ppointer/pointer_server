package com.moplus.moplus_server.domain.problemset.domain;

public enum ProblemSetConfirmStatus {
    CONFIRMED,
    NOT_CONFIRMED;
    public ProblemSetConfirmStatus toggle() {
        return this == CONFIRMED ? NOT_CONFIRMED : CONFIRMED;
    }
}