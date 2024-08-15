package com.epm.gestepm.lib.audit;

import java.time.OffsetDateTime;

public interface AuditCreate extends Auditable {

    default OffsetDateTime getCreatedAt() {
        return null;
    }

    default void setCreatedAt(OffsetDateTime createdAt) {
        // skips
    }

    default String getCreatedBy() {
        return null;
    }

    default void setCreatedBy(String createdBy) {
        // skips
    }

}
