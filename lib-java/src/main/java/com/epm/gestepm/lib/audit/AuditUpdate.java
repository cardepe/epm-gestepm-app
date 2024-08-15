package com.epm.gestepm.lib.audit;

import java.time.OffsetDateTime;

public interface AuditUpdate extends Auditable {

    default OffsetDateTime getUpdatedAt() {
        return null;
    }

    default void setUpdatedAt(OffsetDateTime updatedAt) {
        // skips
    }

    default String getUpdatedBy() {
        return null;
    }

    default void setUpdatedBy(String updatedBy) {
        // skips
    }

    default boolean hasBeenUpdated() {

        boolean hasBeenUpdated = false;
        boolean hasCreation = AuditCreate.class.isAssignableFrom(getClass());

        if (hasCreation) {

            final OffsetDateTime createdAt = ((AuditCreate) this).getCreatedAt();
            final OffsetDateTime updatedAt = getUpdatedAt();

            if (createdAt != null && updatedAt != null) {
                hasBeenUpdated = !createdAt.isEqual(updatedAt);
            } else {
                hasBeenUpdated = updatedAt != null || getUpdatedBy() != null;
            }

        } else if (getUpdatedAt() != null || getUpdatedBy() != null) {
            hasBeenUpdated = true;
        }

        return hasBeenUpdated;

    }

}
