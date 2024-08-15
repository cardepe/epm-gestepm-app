package com.epm.gestepm.lib.applocale.model.dao.mappers;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.nullableInt;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.epm.gestepm.lib.applocale.model.dao.entity.AppLocale;
import com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils;
import org.springframework.jdbc.core.RowMapper;

public class AppLocaleRowMapper implements RowMapper<AppLocale> {

    public static final String COL_AL_ID = "app_locale_id";

    public static final String COL_AL_ISO = "app_locale_iso";

    public static final String COL_AL_NAME = "app_locale_name";

    public static final String AL_IS_DEFAULT = "app_locale_is_default";

    @Override
    public AppLocale mapRow(ResultSet resultSet, int i) throws SQLException {

        final AppLocale appLocale = new AppLocale();

        appLocale.setAppLocaleId(ResultSetMappingUtils.nullableInt(resultSet, COL_AL_ID));
        appLocale.setAppLocale(resultSet.getString(COL_AL_ISO));
        appLocale.setName(resultSet.getString(COL_AL_NAME));
        appLocale.setIsDefault(resultSet.getBoolean(AL_IS_DEFAULT));

        return appLocale;
    }

}
