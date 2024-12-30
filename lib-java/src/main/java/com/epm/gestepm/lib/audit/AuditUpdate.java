package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface AuditUpdate extends Auditable {

    default LocalDateTime getUpdatedAt() {
        return null;
    }

    default void setUpdatedAt(LocalDateTime updatedAt) {
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

            final LocalDateTime createdAt = ((AuditCreate) this).getCreatedAt();
            final LocalDateTime updatedAt = getUpdatedAt();

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
