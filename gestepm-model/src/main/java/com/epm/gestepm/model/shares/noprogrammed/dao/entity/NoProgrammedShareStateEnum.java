package com.epm.gestepm.model.shares.noprogrammed.dao.entity;

public enum NoProgrammedShareStateEnum {

    NEW(1),
    INITIALIZED(2),
    IN_PROGRESS(3),
    CLOSED(4);

    final Integer state;

    NoProgrammedShareStateEnum(final Integer state) {
        this.state = state;
    }
}
