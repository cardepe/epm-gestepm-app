package com.epm.gestepm.model.inspection.dao.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActionEnum {

    INTERVENTION(1),
    DIAGNOSIS(2),
    FOLLOWING(3);

    final Integer id;

    @JsonCreator
    public static ActionEnum fromValue(Integer id) {
        for (ActionEnum b : ActionEnum.values()) {
            if (b.id.equals(id)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + id + "'");
    }
}
