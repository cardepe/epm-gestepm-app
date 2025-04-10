package com.epm.gestepm.model.shares.displacement.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.shares.displacement.dao.entity.DisplacementShare;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public class DisplacementShareRowMapper extends CommonRowMapper implements RowMapper<DisplacementShare> {

    public static final String COL_DI_ID = "displacement_share_id";

    public static final String COL_DI_U_ID = "user_id";

    public static final String COL_DI_U_USERNAME = "username";

    public static final String COL_DI_P_ID = "project_id";

    public static final String COL_DI_P_NAME = "project_name";

    public static final String COL_DI_DESCRIPTION = "description";

    public static final String COL_DI_START_DATE = "start_date";

    public static final String COL_DI_END_DATE = "end_date";

    public static final String COL_DI_OBSERVATIONS = "observations";

    @Override
    public DisplacementShare mapRow(ResultSet rs, int rowNum) throws SQLException {

        final DisplacementShare displacementShare = new DisplacementShare();
        displacementShare.setId(rs.getInt(COL_DI_ID));
        displacementShare.setUserId(rs.getInt(COL_DI_U_ID));
        displacementShare.setUsername(rs.getString(COL_DI_U_USERNAME));
        displacementShare.setProjectId(rs.getInt(COL_DI_P_ID));
        displacementShare.setProjectName(rs.getString(COL_DI_P_NAME));
        displacementShare.setDescription(rs.getString(COL_DI_DESCRIPTION));
        displacementShare.setStartDate(rs.getTimestamp(COL_DI_START_DATE).toLocalDateTime());
        displacementShare.setEndDate(rs.getTimestamp(COL_DI_END_DATE).toLocalDateTime());
        displacementShare.setObservations(nullableString(rs, COL_DI_OBSERVATIONS));

        this.setCommonAudit(displacementShare, rs);

        return displacementShare;
    }
}
