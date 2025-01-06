package com.epm.gestepm.model.personalexpense.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSManyExtractorWithRowMapper;
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpense;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class PersonalExpenseRSManyExtractor extends RSManyExtractorWithRowMapper<PersonalExpense, Integer> {

  @Override
  public RowMapper<PersonalExpense> getRowMapper() {
    return new PersonalExpenseRowMapper();
  }

  @Override
  public BinaryOperator<PersonalExpense> getReducer() {
    return new PersonalExpenseRSReducer();
  }

  @Override
  public Function<PersonalExpense, Integer> getKeyMatcher() {
    return PersonalExpense::getId;
  }

}
