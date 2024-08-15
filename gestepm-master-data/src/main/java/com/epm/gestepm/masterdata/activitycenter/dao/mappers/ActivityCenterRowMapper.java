package com.epm.gestepm.masterdata.activitycenter.dao.mappers;

import com.epm.gestepm.masterdata.activitycenter.dao.entity.ActivityCenter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityCenterRowMapper implements RowMapper<ActivityCenter> {

  public static final String COL_AC_ID = "activity_center_id";

  public static final String COL_AC_NAME = "name";

  public static final String COL_AC_COUNTRY_ID = "country_id";

  @Override
  public ActivityCenter mapRow(ResultSet resultSet, int i) throws SQLException {

    final ActivityCenter activityCenter = new ActivityCenter();

    activityCenter.setId(resultSet.getInt(COL_AC_ID));
    activityCenter.setName(resultSet.getString(COL_AC_NAME));
    activityCenter.setCountryId(resultSet.getInt(COL_AC_COUNTRY_ID));

    return activityCenter;
  }

}
