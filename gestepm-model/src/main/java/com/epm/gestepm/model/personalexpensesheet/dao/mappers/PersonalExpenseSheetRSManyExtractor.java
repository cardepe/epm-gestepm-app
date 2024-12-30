package com.epm.gestepm.model.personalexpensesheet.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSManyExtractorWithRowMapper;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheet;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class PersonalExpenseSheetRSManyExtractor extends RSManyExtractorWithRowMapper<PersonalExpenseSheet, Integer> {

  @Override
  public RowMapper<PersonalExpenseSheet> getRowMapper() {
    return new PersonalExpenseSheetRowMapper();
  }

  @Override
  public BinaryOperator<PersonalExpenseSheet> getReducer() {
    return new PersonalExpenseSheetRSReducer();
  }

  @Override
  public Function<PersonalExpenseSheet, Integer> getKeyMatcher() {
    return PersonalExpenseSheet::getId;
  }

}
