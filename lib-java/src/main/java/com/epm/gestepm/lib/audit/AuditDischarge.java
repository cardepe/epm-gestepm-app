package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface AuditDischarge extends Auditable {

    default LocalDateTime getDischargedAt() {
        return null;
    }

    default void setDischargedAt(LocalDateTime dischargedAt) {
        // skips
    }

    default String getDischargedBy() {
        return null;
    }

    default void setDischargedBy(String dischargedBy) {
        // skips
    }

}
