package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface AuditCreate extends Auditable {

    default LocalDateTime getCreatedAt() {
        return null;
    }

    default void setCreatedAt(LocalDateTime createdAt) {
        // skips
    }

    default Integer getCreatedBy() {
        return null;
    }

    default void setCreatedBy(Integer createdBy) {
        // skips
    }

}
