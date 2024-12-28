package com.epm.gestepm.model.shares.noprogrammed.dao.mappers;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShareFile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class NoProgrammedShareFileRowMapper implements RowMapper<NoProgrammedShareFile> {

  public static final String COL_NPSF_ID = "no_programmed_share_file_id";

  public static final String COL_NPSF_SHARE_ID = "no_programmed_share_id";

  public static final String COL_NPSF_NAME = "name";

  public static final String COL_NPSF_EXTENSION = "extension";

  public static final String COL_NPSF_CONTENT = "content";

  @Override
  public NoProgrammedShareFile mapRow(ResultSet rs, int i) throws SQLException {

    final NoProgrammedShareFile noProgrammedShareFile = new NoProgrammedShareFile();

    noProgrammedShareFile.setId(rs.getInt(COL_NPSF_ID));
    noProgrammedShareFile.setShareId(rs.getInt(COL_NPSF_SHARE_ID));
    noProgrammedShareFile.setName(rs.getString(COL_NPSF_NAME));
    noProgrammedShareFile.setContent(Base64.getEncoder().encodeToString(FileUtils.decompressBytes(rs.getBytes(COL_NPSF_CONTENT))));
    noProgrammedShareFile.setExt(rs.getString(COL_NPSF_EXTENSION));

    return noProgrammedShareFile;
  }
}
