package com.epm.gestepm.model.user.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.user.dao.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public class UserRowMapper extends CommonRowMapper implements RowMapper<User> {

    public static final String COL_U_ID = "user_id";

    public static final String COL_U_NAME = "name";

    public static final String COL_U_SURNAMES = "surnames";

    public static final String COL_U_EMAIL = "email";

    public static final String COL_U_PASSWORD = "password";

    public static final String COL_U_ACTIVITY_CENTER_ID = "activity_center_id";

    public static final String COL_U_ACTIVITY_CENTER_NAME = "activity_center_name";

    public static final String COL_U_STATE = "state";

    public static final String COL_U_SIGNING_ID = "signing_id";

    public static final String COL_U_FORUM_USERNAME = "forum_username";

    public static final String COL_U_FORUM_PASSWORD = "forum_password";

    public static final String COL_U_ROLE_ID = "role_id";

    public static final String COL_U_ROLE_NAME = "role_name";

    public static final String COL_U_LEVEL_ID = "level_id";

    public static final String COL_U_LEVEL_NAME = "level_name";

    public static final String COL_U_WORKING_HOURS = "working_hours";

    public static final String COL_U_CURRENT_YEAR_HOLIDAYS_COUNT = "current_year_holidays_count";

    public static final String COL_U_LAST_YEAR_HOLIDAYS_COUNT = "last_year_holidays_count";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        final User user = new User();
        user.setId(rs.getInt(COL_U_ID));
        user.setName(rs.getString(COL_U_NAME));
        user.setSurnames(rs.getString(COL_U_SURNAMES));
        user.setEmail(rs.getString(COL_U_EMAIL));
        user.setPassword(rs.getString(COL_U_PASSWORD));
        user.setActivityCenterId(rs.getInt(COL_U_ACTIVITY_CENTER_ID));
        user.setState(rs.getInt(COL_U_STATE));
        user.setSigningId(rs.getInt(COL_U_SIGNING_ID));
        user.setForumUsername(nullableString(rs, COL_U_FORUM_USERNAME));
        user.setForumPassword(nullableString(rs, COL_U_FORUM_PASSWORD));
        user.setRoleId(rs.getInt(COL_U_ROLE_ID));
        user.setLevelId(nullableInt(rs, COL_U_LEVEL_ID));
        user.setWorkingHours(nullableDouble(rs, COL_U_WORKING_HOURS));
        user.setCurrentYearHolidaysCount(nullableInt(rs, COL_U_CURRENT_YEAR_HOLIDAYS_COUNT));
        user.setLastYearHolidaysCount(nullableInt(rs, COL_U_LAST_YEAR_HOLIDAYS_COUNT));

        return user;
    }
}
