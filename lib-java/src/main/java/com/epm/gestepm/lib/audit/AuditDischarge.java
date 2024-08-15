package com.epm.gestepm.lib.audit;

import java.time.OffsetDateTime;

public interface AuditDischarge extends Auditable {

    default OffsetDateTime getDischargedAt() {
        return null;
    }

    default void setDischargedAt(OffsetDateTime dischargedAt) {
        // skips
    }

    default String getDischargedBy() {
        return null;
    }

    default void setDischargedBy(String dischargedBy) {
        // skips
    }

}
