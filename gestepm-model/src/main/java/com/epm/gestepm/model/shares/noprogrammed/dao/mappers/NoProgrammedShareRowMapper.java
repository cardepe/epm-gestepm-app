package com.epm.gestepm.model.shares.noprogrammed.dao.mappers;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShareStateEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.jdbc.utils.ResultSetMappingUtils.*;

public class NoProgrammedShareRowMapper implements RowMapper<NoProgrammedShare> {

  public static final String COL_NPS_ID = "no_programmed_share_id";

  public static final String COL_NPS_USER_ID = "user_id";

  public static final String COL_NPS_PROJECT_ID = "project_id";

  public static final String COL_NPS_USER_SIGNING_ID = "user_signing_id";

  public static final String COL_NPS_START_DATE = "start_date";

  public static final String COL_NPS_END_DATE = "end_date";

  public static final String COL_NPS_DESCRIPTION = "description";

  public static final String COL_NPS_FAMILY_ID = "family_id";

  public static final String COL_NPS_SUB_FAMILY_ID = "sub_family_id";

  public static final String COL_NPS_TOPIC_ID = "topic_id";

  public static final String COL_NPS_FORUM_TITLE = "forum_title";

  public static final String COL_NPS_STATE = "state";

  public static final String COL_NPS_INSPECTION_ID = "inspection_id";

  public static final String COL_NPS_FILE_ID = "no_programmed_share_file_id";

  @Override
  public NoProgrammedShare mapRow(ResultSet rs, int i) throws SQLException {

    final NoProgrammedShare noProgrammedShare = new NoProgrammedShare();

    noProgrammedShare.setId(rs.getInt(COL_NPS_ID));
    noProgrammedShare.setUserId(rs.getInt(COL_NPS_USER_ID));
    noProgrammedShare.setProjectId(rs.getInt(COL_NPS_PROJECT_ID));
    noProgrammedShare.setUserSigningId(nullableInt(rs, COL_NPS_USER_SIGNING_ID));
    noProgrammedShare.setStartDate(rs.getTimestamp(COL_NPS_START_DATE).toInstant().atOffset(ZoneOffset.UTC));
    noProgrammedShare.setEndDate(nullableOffsetDateTime(rs, COL_NPS_END_DATE));
    noProgrammedShare.setDescription(nullableString(rs, COL_NPS_DESCRIPTION));
    noProgrammedShare.setFamilyId(nullableInt(rs, COL_NPS_FAMILY_ID));
    noProgrammedShare.setSubFamilyId(nullableInt(rs, COL_NPS_SUB_FAMILY_ID));
    noProgrammedShare.setTopicId(nullableInt(rs, COL_NPS_TOPIC_ID));
    noProgrammedShare.setForumTitle(nullableString(rs, COL_NPS_FORUM_TITLE));
    noProgrammedShare.setState(NoProgrammedShareStateEnum.fromValue(rs.getInt(COL_NPS_STATE)));

    final List<Integer> inspectionIds = new ArrayList<>();

    if (hasValue(rs, COL_NPS_INSPECTION_ID)) {
      inspectionIds.add(rs.getInt(COL_NPS_INSPECTION_ID));
    }

    noProgrammedShare.setInspectionIds(inspectionIds);

    final Set<Integer> fileIds = new HashSet<>();

    if (hasValue(rs, COL_NPS_FILE_ID)) {
      fileIds.add(rs.getInt(COL_NPS_FILE_ID));
    }

    noProgrammedShare.setFileIds(fileIds);

    return noProgrammedShare;
  }

}
