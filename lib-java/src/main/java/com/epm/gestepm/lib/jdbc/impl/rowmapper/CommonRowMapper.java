package com.epm.gestepm.lib.jdbc.impl.rowmapper;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.nullableLocalDateTime;
import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.nullableString;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.epm.gestepm.lib.audit.AuditCreate;
import com.epm.gestepm.lib.audit.AuditCreateDelete;
import com.epm.gestepm.lib.audit.AuditCreateDischarge;
import com.epm.gestepm.lib.audit.AuditCreateUpdate;
import com.epm.gestepm.lib.audit.AuditCreateUpdateDelete;
import com.epm.gestepm.lib.audit.AuditCreateUpdateDischarge;
import com.epm.gestepm.lib.audit.AuditDelete;
import com.epm.gestepm.lib.audit.AuditDeprecate;
import com.epm.gestepm.lib.audit.AuditDischarge;
import com.epm.gestepm.lib.audit.AuditLastAccess;
import com.epm.gestepm.lib.audit.AuditUpdate;

public abstract class CommonRowMapper {

    public static final String COL_CREATED_AT = "FECHA_ALTA";

    public static final String COL_CREATED_BY = "USUARIO_ALTA";

    public static final String COL_MODIFY_AT = "FECHA_MODIFICACION";

    public static final String COL_MODIFY_BY = "USUARIO_MODIFICACION";

    public static final String COL_DISCHARGE_AT = "FECHA_BAJA";

    public static final String COL_DISCHARGE_BY = "USUARIO_BAJA";

    public static final String COL_DELETED_AT = "FECHA_BORRADO";

    public static final String COL_DELETED_BY = "USUARIO_BORRADO";

    public static final String COL_DEPRECATED_AT = "FECHA_DEPRECADO";

    public static final String COL_DEPRECATED_BY = "USUARIO_DEPRECADO";

    public static final String COL_LAST_ACCESS_AT = "FECHA_ULTIMO_ACCESO";

    public static final String COL_LAST_ACCESS_BY = "USUARIO_ULTIMO_ACCESO";

    protected void setCommonAudit(AuditCreate result, final ResultSet resultSet) throws SQLException {

        result.setCreatedAt(nullableLocalDateTime(resultSet, COL_CREATED_AT));
        result.setCreatedBy(nullableString(resultSet, COL_CREATED_BY));
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

    protected void setCommonAudit(AuditDelete result, final ResultSet resultSet) throws SQLException {
        result.setDeletedAt(nullableLocalDateTime(resultSet, COL_DELETED_AT));
        result.setDeletedBy(nullableString(resultSet, COL_DELETED_BY));
    }

    protected void setCommonAudit(AuditDeprecate result, final ResultSet resultSet) throws SQLException {
        result.setDeprecatedAt(nullableLocalDateTime(resultSet, COL_DEPRECATED_AT));
        result.setDeprecatedBy(nullableString(resultSet, COL_DEPRECATED_BY));
    }

    protected void setCommonAudit(AuditDischarge result, final ResultSet resultSet) throws SQLException {
        result.setDischargedAt(nullableLocalDateTime(resultSet, COL_DISCHARGE_AT));
        result.setDischargedBy(nullableString(resultSet, COL_DISCHARGE_BY));
    }

    protected void setCommonAudit(AuditLastAccess result, final ResultSet resultSet) throws SQLException {
        result.setLastAccessedAt(nullableLocalDateTime(resultSet, COL_LAST_ACCESS_AT));
        result.setLastAccessedBy(nullableString(resultSet, COL_LAST_ACCESS_BY));
    }

    protected void setCommonAudit(AuditUpdate result, final ResultSet resultSet) throws SQLException {
        result.setUpdatedAt(nullableLocalDateTime(resultSet, COL_MODIFY_AT));
        result.setUpdatedBy(nullableString(resultSet, COL_MODIFY_BY));
    }

}
