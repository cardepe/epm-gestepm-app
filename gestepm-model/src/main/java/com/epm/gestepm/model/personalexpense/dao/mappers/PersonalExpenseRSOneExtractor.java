package com.epm.gestepm.model.personalexpense.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSOneExtractorWithRowMapper;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpense;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;

public class PersonalExpenseRSOneExtractor extends RSOneExtractorWithRowMapper<PersonalExpense, Integer> {

  @Override
  public RowMapper<PersonalExpense> getRowMapper() {
    return new PersonalExpenseRowMapper();
  }

  @Override
  public BinaryOperator<PersonalExpense> getReducer() {
    return new PersonalExpenseRSReducer();
  }

}
