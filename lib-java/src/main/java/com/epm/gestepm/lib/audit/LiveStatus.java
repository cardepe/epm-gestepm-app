package com.epm.gestepm.lib.audit;

public enum LiveStatus {

    ACTIVE, NOT_ACTIVE_YET, DISCHARGED, DELETED, DEPRECATED, NONE;

    public boolean is(LiveStatus statusToCompare) {
        return this.equals(statusToCompare);
    }

    public boolean isNot(LiveStatus statusToCompare) {
        return !is(statusToCompare);
    }

}
