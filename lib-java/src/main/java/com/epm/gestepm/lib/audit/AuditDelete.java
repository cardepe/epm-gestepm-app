package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface AuditDelete extends Auditable {

    default LocalDateTime getDeletedAt() {
        return null;
    }

    default void setDeletedAt(LocalDateTime deletedAt) {
        // skips
    }

    default Integer getDeletedBy() {
        return null;
    }

    default void setDeletedBy(Integer deletedBy) {
        // skips
    }

}
