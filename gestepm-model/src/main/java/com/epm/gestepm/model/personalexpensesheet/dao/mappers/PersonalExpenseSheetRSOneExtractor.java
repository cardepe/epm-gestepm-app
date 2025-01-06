package com.epm.gestepm.model.personalexpensesheet.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSOneExtractorWithRowMapper;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheet;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;

public class PersonalExpenseSheetRSOneExtractor extends RSOneExtractorWithRowMapper<PersonalExpenseSheet, Integer> {

  @Override
  public RowMapper<PersonalExpenseSheet> getRowMapper() {
    return new PersonalExpenseSheetRowMapper();
  }

  @Override
  public BinaryOperator<PersonalExpenseSheet> getReducer() {
    return new PersonalExpenseSheetRSReducer();
  }

}
