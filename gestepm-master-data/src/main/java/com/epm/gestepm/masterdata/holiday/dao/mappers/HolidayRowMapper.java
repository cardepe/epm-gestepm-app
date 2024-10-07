package com.epm.gestepm.masterdata.holiday.dao.mappers;

import com.epm.gestepm.masterdata.holiday.dao.entity.Holiday;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.nullableInt;

public class HolidayRowMapper implements RowMapper<Holiday> {

    public static final String COL_H_ID = "holiday_id";

    public static final String COL_H_NAME = "name";

    public static final String COL_H_DAY = "day";

    public static final String COL_H_MONTH = "month";

    public static final String COL_H_C_ID = "country_id";

    public static final String COL_H_AC_ID = "activity_center_id";

    @Override
    public Holiday mapRow(ResultSet resultSet, int i) throws SQLException {

        final Holiday holiday = new Holiday();

        holiday.setId(resultSet.getInt(COL_H_ID));
        holiday.setName(resultSet.getString(COL_H_NAME));
        holiday.setDay(resultSet.getInt(COL_H_DAY));
        holiday.setMonth(resultSet.getInt(COL_H_MONTH));
        holiday.setCountryId(resultSet.getInt(COL_H_C_ID));
        holiday.setActivityCenterId(nullableInt(resultSet, COL_H_AC_ID));

        return holiday;
    }
}
