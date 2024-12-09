package com.epm.gestepm.model.shares.noprogrammed.dao.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoProgrammedShareStateEnum {

    NEW(1),
    INITIALIZED(2),
    IN_PROGRESS(3),
    CLOSED(4);

    final Integer id;

    @JsonCreator
    public static NoProgrammedShareStateEnum fromValue(Integer id) {
        for (NoProgrammedShareStateEnum b : NoProgrammedShareStateEnum.values()) {
            if (b.id.equals(id)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + id + "'");
    }
}
