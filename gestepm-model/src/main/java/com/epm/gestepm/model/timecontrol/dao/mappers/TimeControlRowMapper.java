package com.epm.gestepm.model.timecontrol.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.timecontrol.dao.entity.TimeControl;
import com.epm.gestepm.model.timecontrol.dao.entity.TimeControlTypeEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.nullableString;

public class TimeControlRowMapper extends CommonRowMapper implements RowMapper<TimeControl> {

    public static final String COL_TC_ID = "id";

    public static final String COL_TC_USER_ID = "user_id";

    public static final String COL_TC_START_DATE = "start_date";

    public static final String COL_TC_END_DATE = "end_date";

    public static final String COL_TC_DESCRIPTION = "description";

    public static final String COL_TC_TYPE = "type";

    public static final String COL_TC_DETAIL_URL = "detail_url";

    @Override
    public TimeControl mapRow(ResultSet resultSet, int i) throws SQLException {

        final TimeControl timeControl = new TimeControl();

        timeControl.setId(resultSet.getInt(COL_TC_ID));
        timeControl.setUserId(resultSet.getInt(COL_TC_USER_ID));
        timeControl.setStartDate(resultSet.getTimestamp(COL_TC_START_DATE).toLocalDateTime());
        timeControl.setEndDate(resultSet.getTimestamp(COL_TC_END_DATE).toLocalDateTime());
        timeControl.setDescription(resultSet.getString(COL_TC_DESCRIPTION));
        timeControl.setType(TimeControlTypeEnum.valueOf(resultSet.getString(COL_TC_TYPE)));
        timeControl.setDetailUrl(nullableString(resultSet, COL_TC_DETAIL_URL));

        return timeControl;
    }
}
