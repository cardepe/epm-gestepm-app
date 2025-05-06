package com.epm.gestepm.model.shares.work.dao.mappers;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.shares.work.dao.entity.WorkShareFile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class WorkShareFileRowMapper implements RowMapper<WorkShareFile> {

  public static final String COL_WSF_ID = "work_share_file_id";

  public static final String COL_WSF_SHARE_ID = "work_share_id";

  public static final String COL_WSF_NAME = "name";

  public static final String COL_WSF_CONTENT = "content";

  @Override
  public WorkShareFile mapRow(ResultSet rs, int i) throws SQLException {

    final WorkShareFile workShareFile = new WorkShareFile();

    workShareFile.setId(rs.getInt(COL_WSF_ID));
    workShareFile.setShareId(rs.getInt(COL_WSF_SHARE_ID));
    workShareFile.setName(rs.getString(COL_WSF_NAME));
    workShareFile.setContent(Base64.getEncoder().encodeToString(FileUtils.decompressBytes(rs.getBytes(COL_WSF_CONTENT))));

    return workShareFile;
  }
}
