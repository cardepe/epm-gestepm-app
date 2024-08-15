package com.epm.gestepm.modelapi.shares.noprogrammed.dto;

public enum NoProgrammedShareStateEnumDto {

    NEW(1),
    INITIALIZED(2),
    IN_PROGRESS(3),
    CLOSED(4);

    final Integer state;

    NoProgrammedShareStateEnumDto(final Integer state) {
        this.state = state;
    }
}
