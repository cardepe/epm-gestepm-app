package com.epm.gestepm.model.shares.share.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.shares.share.dao.entity.Share;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public class ShareRowMapper extends CommonRowMapper implements RowMapper<Share> {

    public static final String COL_S_ID = "share_id";

    public static final String COL_S_U_ID = "user_id";

    public static final String COL_S_U_USERNAME = "username";

    public static final String COL_S_P_ID = "project_id";

    public static final String COL_S_P_NAME = "project_name";

    public static final String COL_S_START_DATE = "start_date";

    public static final String COL_S_END_DATE = "end_date";

    public static final String COL_S_TYPE = "type";

    public static final String COL_S_DETAIL_URL = "detail_url";

    @Override
    public Share mapRow(ResultSet rs, int rowNum) throws SQLException {

        final Share share = new Share();
        share.setId(rs.getInt(COL_S_ID));
        share.setUserId(rs.getInt(COL_S_U_ID));
        share.setProjectId(rs.getInt(COL_S_P_ID));
        share.setStartDate(rs.getTimestamp(COL_S_START_DATE).toLocalDateTime());
        share.setEndDate(nullableLocalDateTime(rs, COL_S_END_DATE));
        share.setType(rs.getString(COL_S_TYPE));
        share.setDetailUrl(rs.getString(COL_S_DETAIL_URL));

        return share;
    }
}
