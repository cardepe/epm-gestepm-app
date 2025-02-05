package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface AuditApprove extends Auditable {

    default LocalDateTime getApprovedAt() {
        return null;
    }

    default void setApprovedAt(LocalDateTime updatedAt) {
        // skips
    }

    default Integer getApprovedBy() {
        return null;
    }

    default void setApprovedBy(Integer updatedBy) {
        // skips
    }

    default boolean hasBeenApproved() {

        boolean hasBeenApproved = false;
        boolean hasCreation = AuditCreate.class.isAssignableFrom(getClass());

        if (hasCreation) {

            final LocalDateTime createdAt = ((AuditCreate) this).getCreatedAt();
            final LocalDateTime updatedAt = getApprovedAt();

            if (createdAt != null && updatedAt != null) {
                hasBeenApproved = !createdAt.isEqual(updatedAt);
            } else {
                hasBeenApproved = updatedAt != null || getApprovedBy() != null;
            }

        } else if (getApprovedAt() != null || getApprovedBy() != null) {
            hasBeenApproved = true;
        }

        return hasBeenApproved;

    }

}
