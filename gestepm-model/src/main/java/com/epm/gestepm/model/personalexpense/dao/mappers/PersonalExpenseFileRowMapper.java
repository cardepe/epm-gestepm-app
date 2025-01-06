package com.epm.gestepm.model.personalexpense.dao.mappers;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpenseFile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class PersonalExpenseFileRowMapper implements RowMapper<PersonalExpenseFile> {

  public static final String COL_PEF_ID = "personal_expense_file_id";

  public static final String COL_PEF_SHARE_ID = "personal_expense_id";

  public static final String COL_PEF_NAME = "name";

  public static final String COL_PEF_CONTENT = "content";

  @Override
  public PersonalExpenseFile mapRow(ResultSet rs, int i) throws SQLException {

    final PersonalExpenseFile personalExpenseFile = new PersonalExpenseFile();

    personalExpenseFile.setId(rs.getInt(COL_PEF_ID));
    personalExpenseFile.setPersonalExpenseId(rs.getInt(COL_PEF_SHARE_ID));
    personalExpenseFile.setName(rs.getString(COL_PEF_NAME));
    personalExpenseFile.setContent(Base64.getEncoder().encodeToString(FileUtils.decompressBytes(rs.getBytes(COL_PEF_CONTENT))));

    return personalExpenseFile;
  }
}
