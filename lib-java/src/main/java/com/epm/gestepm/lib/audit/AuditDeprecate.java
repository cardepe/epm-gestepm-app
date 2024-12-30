package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface AuditDeprecate extends Auditable {

    default LocalDateTime getDeprecatedAt() {
        return null;
    }

    default void setDeprecatedAt(LocalDateTime deprecatedAt) {
        // skips
    }

    default String getDeprecatedBy() {
        return null;
    }

    default void setDeprecatedBy(String deprecatedBy) {
        // skips
    }

}
