package com.epm.gestepm.lib.audit;

import java.time.LocalDateTime;

public interface AuditProvider {

    default void auditApprove(AuditApprove auditApprove) {
        auditApprove.setApprovedBy(login());
        auditApprove.setApprovedAt(time());
    }

    default void clearApprove(AuditApprove auditApprove) {
        auditApprove.setApprovedBy(null);
        auditApprove.setApprovedAt(null);
    }
    
    default void auditLastAccess(AuditLastAccess auditLastAccess) {
        auditLastAccess.setLastAccessedBy(login());
        auditLastAccess.setLastAccessedAt(time());
    }

    default void clearLastAccess(AuditLastAccess auditLastAccess) {
        auditLastAccess.setLastAccessedBy(null);
        auditLastAccess.setLastAccessedAt(null);
    }

    default void auditCreate(AuditCreate auditCreate) {
        auditCreate.setCreatedBy(login());
        auditCreate.setCreatedAt(time());
    }

    default void clearCreate(AuditCreate auditCreate) {
        auditCreate.setCreatedBy(null);
        auditCreate.setCreatedAt(null);
    }

    default void auditClose(AuditClose auditClose) {
        auditClose.setClosedBy(login());
        auditClose.setClosedAt(time());
    }

    default void clearClose(AuditClose auditClose) {
        auditClose.setClosedBy(null);
        auditClose.setClosedAt(null);
    }

    default void auditUpdate(AuditUpdate auditUpdate) {
        auditUpdate.setUpdatedBy(login());
        auditUpdate.setUpdatedAt(time());
    }

    default void clearUpdate(AuditUpdate auditUpdate) {
        auditUpdate.setUpdatedBy(null);
        auditUpdate.setUpdatedAt(null);
    }

    default void auditDischarge(AuditDischarge auditDischarge) {
        auditDischarge.setDischargedBy(login());
        auditDischarge.setDischargedAt(time());
    }

    default void clearDischarge(AuditDischarge auditDischarge) {
        auditDischarge.setDischargedBy(null);
        auditDischarge.setDischargedAt(null);
    }

    default void auditDelete(AuditDelete auditDelete) {
        auditDelete.setDeletedBy(login());
        auditDelete.setDeletedAt(time());
    }

    default void clearDelete(AuditDelete auditDelete) {
        auditDelete.setDeletedBy(null);
        auditDelete.setDeletedAt(null);
    }

    default void auditDeprecate(AuditDeprecate auditDeprecate) {
        auditDeprecate.setDeprecatedBy(login());
        auditDeprecate.setDeprecatedAt(time());
    }

    default void clearDeprecate(AuditDeprecate auditDeprecate) {
        auditDeprecate.setDeprecatedBy(null);
        auditDeprecate.setDeprecatedAt(null);
    }

    default void auditPaid(AuditPaid auditPaid) {
        auditPaid.setPaidBy(login());
        auditPaid.setPaidAt(time());
    }

    default void clearPaid(AuditPaid auditPaid) {
        auditPaid.setPaidBy(null);
        auditPaid.setPaidAt(null);
    }

    Integer login();

    LocalDateTime time();

}
