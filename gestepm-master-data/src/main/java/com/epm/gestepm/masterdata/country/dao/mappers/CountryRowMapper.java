package com.epm.gestepm.masterdata.country.dao.mappers;

import com.epm.gestepm.masterdata.country.dao.entity.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {

  public static final String COL_C_ID = "country_id";

  public static final String COL_C_NAME = "name";

  public static final String COL_C_TAG = "tag";

  @Override
  public Country mapRow(ResultSet resultSet, int i) throws SQLException {

    final Country country = new Country();

    country.setId(resultSet.getInt(COL_C_ID));
    country.setName(resultSet.getString(COL_C_NAME));
    country.setTag(resultSet.getString(COL_C_TAG));

    return country;
  }

}
