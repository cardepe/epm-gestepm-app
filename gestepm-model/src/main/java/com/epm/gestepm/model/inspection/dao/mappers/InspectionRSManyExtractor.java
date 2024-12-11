package com.epm.gestepm.model.inspection.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSManyExtractorWithRowMapper;
import com.epm.gestepm.model.inspection.dao.entity.Inspection;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class InspectionRSManyExtractor extends RSManyExtractorWithRowMapper<Inspection, Integer> {

  @Override
  public RowMapper<Inspection> getRowMapper() {
    return new InspectionRowMapper();
  }

  @Override
  public BinaryOperator<Inspection> getReducer() {
    return new InspectionRSReducer();
  }

  @Override
  public Function<Inspection, Integer> getKeyMatcher() {
    return Inspection::getId;
  }

}
