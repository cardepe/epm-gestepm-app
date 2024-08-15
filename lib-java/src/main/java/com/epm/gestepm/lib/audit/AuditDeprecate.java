package com.epm.gestepm.lib.audit;

import java.time.OffsetDateTime;

public interface AuditDeprecate extends Auditable {

    default OffsetDateTime getDeprecatedAt() {
        return null;
    }

    default void setDeprecatedAt(OffsetDateTime deprecatedAt) {
        // skips
    }

    default String getDeprecatedBy() {
        return null;
    }

    default void setDeprecatedBy(String deprecatedBy) {
        // skips
    }

}
