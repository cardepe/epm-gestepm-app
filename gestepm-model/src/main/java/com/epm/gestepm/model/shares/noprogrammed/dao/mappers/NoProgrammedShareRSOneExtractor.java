package com.epm.gestepm.model.shares.noprogrammed.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSOneExtractorWithRowMapper;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;

public class NoProgrammedShareRSOneExtractor extends RSOneExtractorWithRowMapper<NoProgrammedShare, Integer> {

  @Override
  public RowMapper<NoProgrammedShare> getRowMapper() {
    return new NoProgrammedShareRowMapper();
  }

  @Override
  public BinaryOperator<NoProgrammedShare> getReducer() {
    return new NoProgrammedShareRSReducer();
  }

}
