package com.epm.gestepm.lib.audit;

import java.time.OffsetDateTime;

public interface AuditLastAccess extends Auditable {

    default OffsetDateTime getLastAccessedAt() {
        return null;
    }

    default void setLastAccessedAt(OffsetDateTime lastAccessedAt) {
        // skips
    }

    default String getLastAccessedBy() {
        return null;
    }

    default void setLastAccessedBy(String lastAccessedBy) {
        // skips
    }

}
