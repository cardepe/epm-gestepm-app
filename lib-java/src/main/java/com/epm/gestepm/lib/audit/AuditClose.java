package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface AuditClose extends Auditable {

    default LocalDateTime getClosedAt() {
        return null;
    }

    default void setClosedAt(LocalDateTime closedAt) {
        // skips
    }

    default Integer getClosedBy() {
        return null;
    }

    default void setClosedBy(Integer closedBy) {
        // skips
    }

}
