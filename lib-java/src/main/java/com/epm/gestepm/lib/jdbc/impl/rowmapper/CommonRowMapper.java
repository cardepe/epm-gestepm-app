package com.epm.gestepm.lib.jdbc.impl.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.epm.gestepm.lib.audit.*;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public abstract class CommonRowMapper {

    public static final String COL_CREATED_AT = "created_at";

    public static final String COL_CREATED_BY = "created_by";

    public static final String COL_CLOSED_AT = "closed_at";

    public static final String COL_CLOSED_BY = "closed_by";

    public static final String COL_MODIFY_AT = "modified_at";

    public static final String COL_MODIFY_BY = "modified_by";

    public static final String COL_DISCHARGE_AT = "discharged_at";

    public static final String COL_DISCHARGE_BY = "discharged_by";

    public static final String COL_DELETED_AT = "FECHA_BORRADO";

    public static final String COL_DELETED_BY = "USUARIO_BORRADO";

    public static final String COL_DEPRECATED_AT = "FECHA_DEPRECADO";

    public static final String COL_DEPRECATED_BY = "USUARIO_DEPRECADO";

    public static final String COL_LAST_ACCESS_AT = "FECHA_ULTIMO_ACCESO";

    public static final String COL_LAST_ACCESS_BY = "USUARIO_ULTIMO_ACCESO";

    public static final String COL_APPROVED_AT = "approved_at";

    public static final String COL_APPROVED_BY = "approved_by";

    public static final String COL_PAID_AT = "paid_at";

    public static final String COL_PAID_BY = "paid_by";

    protected void setCommonAudit(AuditCreate result, final ResultSet resultSet) throws SQLException {

        result.setCreatedAt(nullableLocalDateTime(resultSet, COL_CREATED_AT));
        result.setCreatedBy(nullableInt(resultSet, COL_CREATED_BY));
    }

    protected void setCommonAudit(AuditClose result, final ResultSet resultSet) throws SQLException {

        result.setClosedAt(nullableLocalDateTime(resultSet, COL_CLOSED_AT));
        result.setClosedBy(nullableInt(resultSet, COL_CLOSED_BY));
    }

    protected void setCommonAudit(AuditCreateClose result, final ResultSet resultSet)
            throws SQLException {
        setCommonAudit((AuditCreate) result, resultSet);
        setCommonAudit((AuditClose) result, resultSet);
    }

    protected void setCommonAudit(AuditCreateDelete result, final ResultSet resultSet)
            throws SQLException {
        setCommonAudit((AuditCreate) result, resultSet);
        setCommonAudit((AuditDelete) result, resultSet);
    }

    protected void setCommonAudit(AuditCreateDischarge result, final ResultSet resultSet)
            throws SQLException {
        setCommonAudit((AuditCreate) result, resultSet);
        setCommonAudit((AuditDischarge) result, resultSet);
    }

    protected void setCommonAudit(AuditCreateUpdate result, final ResultSet resultSet)
            throws SQLException {
        setCommonAudit((AuditCreate) result, resultSet);
        setCommonAudit((AuditUpdate) result, resultSet);
    }

    protected void setCommonAudit(AuditCreateUpdateDelete result, final ResultSet resultSet)
            throws SQLException {
        setCommonAudit((AuditCreate) result, resultSet);
        setCommonAudit((AuditUpdate) result, resultSet);
        setCommonAudit((AuditDelete) result, resultSet);
    }

    protected void setCommonAudit(AuditCreateUpdateDischarge result, final ResultSet resultSet)
            throws SQLException {
        setCommonAudit((AuditCreate) result, resultSet);
        setCommonAudit((AuditUpdate) result, resultSet);
        setCommonAudit((AuditDischarge) result, resultSet);
    }

    protected void setCommonAudit(AuditCreateApprovePaidDischarge result, final ResultSet resultSet)
            throws SQLException {
        setCommonAudit((AuditCreate) result, resultSet);
        setCommonAudit((AuditApprove) result, resultSet);
        setCommonAudit((AuditPaid) result, resultSet);
        setCommonAudit((AuditDischarge) result, resultSet);
    }

    protected void setCommonAudit(AuditApprovePaidDischarge result, final ResultSet resultSet)
            throws SQLException {
        setCommonAudit((AuditApprove) result, resultSet);
        setCommonAudit((AuditPaid) result, resultSet);
        setCommonAudit((AuditDischarge) result, resultSet);
    }

    protected void setCommonAudit(AuditDelete result, final ResultSet resultSet) throws SQLException {
        result.setDeletedAt(nullableLocalDateTime(resultSet, COL_DELETED_AT));
        result.setDeletedBy(nullableInt(resultSet, COL_DELETED_BY));
    }

    protected void setCommonAudit(AuditDeprecate result, final ResultSet resultSet) throws SQLException {
        result.setDeprecatedAt(nullableLocalDateTime(resultSet, COL_DEPRECATED_AT));
        result.setDeprecatedBy(nullableInt(resultSet, COL_DEPRECATED_BY));
    }

    protected void setCommonAudit(AuditDischarge result, final ResultSet resultSet) throws SQLException {
        result.setDischargedAt(nullableLocalDateTime(resultSet, COL_DISCHARGE_AT));
        result.setDischargedBy(nullableInt(resultSet, COL_DISCHARGE_BY));
    }

    protected void setCommonAudit(AuditLastAccess result, final ResultSet resultSet) throws SQLException {
        result.setLastAccessedAt(nullableLocalDateTime(resultSet, COL_LAST_ACCESS_AT));
        result.setLastAccessedBy(nullableInt(resultSet, COL_LAST_ACCESS_BY));
    }

    protected void setCommonAudit(AuditUpdate result, final ResultSet resultSet) throws SQLException {
        result.setUpdatedAt(nullableLocalDateTime(resultSet, COL_MODIFY_AT));
        result.setUpdatedBy(nullableInt(resultSet, COL_MODIFY_BY));
    }

    protected void setCommonAudit(AuditApprove result, final ResultSet resultSet) throws SQLException {
        result.setApprovedAt(nullableLocalDateTime(resultSet, COL_APPROVED_AT));
        result.setApprovedBy(nullableInt(resultSet, COL_APPROVED_BY));
    }

    protected void setCommonAudit(AuditPaid result, final ResultSet resultSet) throws SQLException {
        result.setPaidAt(nullableLocalDateTime(resultSet, COL_PAID_AT));
        result.setPaidBy(nullableInt(resultSet, COL_PAID_BY));
    }

}
