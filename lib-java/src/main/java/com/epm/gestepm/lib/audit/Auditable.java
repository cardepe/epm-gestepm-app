package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface Auditable {

    default LiveStatus getLiveStatus() {

        LiveStatus liveStatus = LiveStatus.ACTIVE;

        final boolean hasCreation = AuditCreate.class.isAssignableFrom(getClass());
        final boolean hasDeprecate = AuditDeprecate.class.isAssignableFrom(getClass());
        final boolean hasDischarge = AuditDischarge.class.isAssignableFrom(getClass());
        final boolean hasDelete = AuditDelete.class.isAssignableFrom(getClass());

        if (hasCreation) {

            final LocalDateTime createdAt = ((AuditCreate) this).getCreatedAt();

            if (createdAt != null && createdAt.isAfter(LocalDateTime.now())) {
                liveStatus = LiveStatus.NOT_ACTIVE_YET;
            }
        }

        if (hasDeprecate) {

            final LocalDateTime deprecatedAt = ((AuditDeprecate) this).getDeprecatedAt();

            if (deprecatedAt != null && deprecatedAt.isBefore(LocalDateTime.now())) {
                liveStatus = LiveStatus.DEPRECATED;
            }
        }

        if (hasDischarge) {

            final LocalDateTime dischargedAt = ((AuditDischarge) this).getDischargedAt();

            if (dischargedAt != null && dischargedAt.isBefore(LocalDateTime.now())) {
                liveStatus = LiveStatus.DISCHARGED;
            }
        }

        if (hasDelete) {

            final LocalDateTime deletedAt = ((AuditDelete) this).getDeletedAt();

            if (deletedAt != null && deletedAt.isBefore(LocalDateTime.now())) {
                liveStatus = LiveStatus.DELETED;
            }
        }

        return liveStatus;
    }

    default boolean is(LiveStatus liveStatus) {
        return getLiveStatus().is(liveStatus);
    }

    default boolean isNot(LiveStatus liveStatus) {
        return getLiveStatus().isNot(liveStatus);
    }

}
