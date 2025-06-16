package com.epm.gestepm.model.shares.share.dao.mappers;

import com.epm.gestepm.lib.jdbc.impl.rowmapper.CommonRowMapper;
import com.epm.gestepm.model.inspection.dao.entity.ActionEnum;
import com.epm.gestepm.model.shares.share.dao.entity.Share;
import com.epm.gestepm.model.shares.share.dao.entity.ShareTypeEnum;
import com.epm.gestepm.model.timecontrol.dao.entity.TimeControlTypeEnum;
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
        share.setProjectId(nullableInt(rs, COL_S_P_ID));
        share.setProjectName(nullableString(rs, COL_S_P_NAME));
        share.setStartDate(rs.getTimestamp(COL_S_START_DATE).toLocalDateTime());
        share.setEndDate(nullableLocalDateTime(rs, COL_S_END_DATE));
        share.setType(ShareTypeEnum.valueOf(rs.getString(COL_S_TYPE)));
        share.setDetailUrl(nullableString(rs, COL_S_DETAIL_URL));

        return share;
    }
}
