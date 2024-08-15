package com.epm.gestepm.lib.audit;

import java.time.OffsetDateTime;

public interface Auditable {

    default LiveStatus getLiveStatus() {

        LiveStatus liveStatus = LiveStatus.ACTIVE;

        final boolean hasCreation = AuditCreate.class.isAssignableFrom(getClass());
        final boolean hasDeprecate = AuditDeprecate.class.isAssignableFrom(getClass());
        final boolean hasDischarge = AuditDischarge.class.isAssignableFrom(getClass());
        final boolean hasDelete = AuditDelete.class.isAssignableFrom(getClass());

        if (hasCreation) {

            final OffsetDateTime createdAt = ((AuditCreate) this).getCreatedAt();

            if (createdAt != null && createdAt.isAfter(OffsetDateTime.now())) {
                liveStatus = LiveStatus.NOT_ACTIVE_YET;
            }
        }

        if (hasDeprecate) {

            final OffsetDateTime deprecatedAt = ((AuditDeprecate) this).getDeprecatedAt();

            if (deprecatedAt != null && deprecatedAt.isBefore(OffsetDateTime.now())) {
                liveStatus = LiveStatus.DEPRECATED;
            }
        }

        if (hasDischarge) {

            final OffsetDateTime dischargedAt = ((AuditDischarge) this).getDischargedAt();

            if (dischargedAt != null && dischargedAt.isBefore(OffsetDateTime.now())) {
                liveStatus = LiveStatus.DISCHARGED;
            }
        }

        if (hasDelete) {

            final OffsetDateTime deletedAt = ((AuditDelete) this).getDeletedAt();

            if (deletedAt != null && deletedAt.isBefore(OffsetDateTime.now())) {
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
