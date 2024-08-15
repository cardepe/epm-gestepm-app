package com.epm.gestepm.modelapi.manualsigningtype.dto;

import java.util.Arrays;

public enum ManualSigningTypeEnum {

    UNKNOWN(-1),
    DOCTOR(1),
    EDUCATION(2),
    MOVING(3),
    WEEDING(4),
    PATERNITY(5),
    SERIOUS_ILLNESS(6),
    DEATH(7),
    OTHERS(8);

    private final int id;

    ManualSigningTypeEnum(final int id) {
        this.id = id;
    }

    public static ManualSigningTypeEnum from(final int id) {
        return Arrays.stream(values()).filter(e -> e.getId() == id).findFirst().orElse(UNKNOWN);
    }

    public boolean is(final ManualSigningTypeEnum statusToCompare) {
        return this.equals(statusToCompare);
    }

    public boolean isNot(final ManualSigningTypeEnum statusToCompare) {
        return !this.is(statusToCompare);
    }

    public boolean isSupported() {
        return isNot(UNKNOWN);
    }

    public int getId() {
        return this.id;
    }

}
