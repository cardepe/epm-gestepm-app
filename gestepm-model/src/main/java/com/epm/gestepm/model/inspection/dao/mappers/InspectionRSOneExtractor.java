package com.epm.gestepm.model.inspection.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSOneExtractorWithRowMapper;
import com.epm.gestepm.model.inspection.dao.entity.Inspection;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;

public class InspectionRSOneExtractor extends RSOneExtractorWithRowMapper<Inspection, Integer> {

  @Override
  public RowMapper<Inspection> getRowMapper() {
    return new InspectionRowMapper();
  }

  @Override
  public BinaryOperator<Inspection> getReducer() {
    return new InspectionRSReducer();
  }

}
