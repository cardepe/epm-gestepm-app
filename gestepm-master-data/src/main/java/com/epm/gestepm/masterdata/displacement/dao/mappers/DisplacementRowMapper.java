package com.epm.gestepm.masterdata.displacement.dao.mappers;

import com.epm.gestepm.masterdata.displacement.dao.entity.Displacement;
import com.epm.gestepm.masterdata.displacement.dao.entity.DisplacementType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplacementRowMapper implements RowMapper<Displacement> {

    public static final String COL_D_ID = "displacement_id";

    public static final String COL_D_AC_ID = "activity_center_id";

    public static final String COL_D_NAME = "name";

    public static final String COL_D_TYPE = "type";

    public static final String COL_D_TOTAL_TIME = "total_time";

    @Override
    public Displacement mapRow(ResultSet resultSet, int i) throws SQLException {

        final Displacement displacement = new Displacement();

        displacement.setId(resultSet.getInt(COL_D_ID));
        displacement.setActivityCenterId(resultSet.getInt(COL_D_AC_ID));
        displacement.setName(resultSet.getString(COL_D_NAME));
        displacement.setType(DisplacementType.valueOf(resultSet.getString(COL_D_TYPE)));
        displacement.setTotalTime(resultSet.getInt(COL_D_TOTAL_TIME));

        return displacement;
    }
}
