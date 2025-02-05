package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface AuditLastAccess extends Auditable {

    default LocalDateTime getLastAccessedAt() {
        return null;
    }

    default void setLastAccessedAt(LocalDateTime lastAccessedAt) {
        // skips
    }

    default Integer getLastAccessedBy() {
        return null;
    }

    default void setLastAccessedBy(Integer lastAccessedBy) {
        // skips
    }

}
