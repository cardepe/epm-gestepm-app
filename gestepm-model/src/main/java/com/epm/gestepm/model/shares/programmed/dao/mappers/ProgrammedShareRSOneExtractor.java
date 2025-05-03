package com.epm.gestepm.model.shares.programmed.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSOneExtractorWithRowMapper;
import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShare;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;

public class ProgrammedShareRSOneExtractor extends RSOneExtractorWithRowMapper<ProgrammedShare, Integer> {

    @Override
    public RowMapper<ProgrammedShare> getRowMapper() {
        return new ProgrammedShareRowMapper();
    }

    @Override
    public BinaryOperator<ProgrammedShare> getReducer() {
        return new ProgrammedShareRSReducer();
    }

}
