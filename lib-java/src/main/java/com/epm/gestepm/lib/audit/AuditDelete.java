package com.epm.gestepm.lib.audit;

import java.time.OffsetDateTime;

public interface AuditDelete extends Auditable {

    default OffsetDateTime getDeletedAt() {
        return null;
    }

    default void setDeletedAt(OffsetDateTime deletedAt) {
        // skips
    }

    default String getDeletedBy() {
        return null;
    }

    default void setDeletedBy(String deletedBy) {
        // skips
    }

}
