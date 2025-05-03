package com.epm.gestepm.model.shares.programmed.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSManyExtractorWithRowMapper;
import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShare;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class ProgrammedShareRSManyExtractor extends RSManyExtractorWithRowMapper<ProgrammedShare, Integer> {

    @Override
    public RowMapper<ProgrammedShare> getRowMapper() {
        return new ProgrammedShareRowMapper();
    }

    @Override
    public BinaryOperator<ProgrammedShare> getReducer() {
        return new ProgrammedShareRSReducer();
    }

    @Override
    public Function<ProgrammedShare, Integer> getKeyMatcher() {
        return ProgrammedShare::getId;
    }
}
