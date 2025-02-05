package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface AuditPaid extends Auditable {

    default LocalDateTime getPaidAt() {
        return null;
    }

    default void setPaidAt(LocalDateTime updatedAt) {
        // skips
    }

    default Integer getPaidBy() {
        return null;
    }

    default void setPaidBy(Integer updatedBy) {
        // skips
    }

    default boolean hasBeenPaid() {

        boolean hasBeenPaid = false;
        boolean hasCreation = AuditCreate.class.isAssignableFrom(getClass());

        if (hasCreation) {

            final LocalDateTime createdAt = ((AuditCreate) this).getCreatedAt();
            final LocalDateTime updatedAt = getPaidAt();

            if (createdAt != null && updatedAt != null) {
                hasBeenPaid = !createdAt.isEqual(updatedAt);
            } else {
                hasBeenPaid = updatedAt != null || getPaidBy() != null;
            }

        } else if (getPaidAt() != null || getPaidBy() != null) {
            hasBeenPaid = true;
        }

        return hasBeenPaid;

    }

}
