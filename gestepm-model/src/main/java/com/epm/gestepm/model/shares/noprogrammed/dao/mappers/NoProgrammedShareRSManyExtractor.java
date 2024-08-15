package com.epm.gestepm.model.shares.noprogrammed.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSManyExtractorWithRowMapper;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class NoProgrammedShareRSManyExtractor extends RSManyExtractorWithRowMapper<NoProgrammedShare, Integer> {

  @Override
  public RowMapper<NoProgrammedShare> getRowMapper() {
    return new NoProgrammedShareRowMapper();
  }

  @Override
  public BinaryOperator<NoProgrammedShare> getReducer() {
    return new NoProgrammedShareRSReducer();
  }

  @Override
  public Function<NoProgrammedShare, Integer> getKeyMatcher() {
    return NoProgrammedShare::getId;
  }

}
