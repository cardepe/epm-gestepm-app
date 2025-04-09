package com.epm.gestepm.model.shares.construction.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSManyExtractorWithRowMapper;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShare;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public class ConstructionShareRSManyExtractor extends RSManyExtractorWithRowMapper<ConstructionShare, Integer> {

    @Override
    public RowMapper<ConstructionShare> getRowMapper() {
        return new ConstructionShareRowMapper();
    }

    @Override
    public BinaryOperator<ConstructionShare> getReducer() {
        return new ConstructionShareRSReducer();
    }

    @Override
    public Function<ConstructionShare, Integer> getKeyMatcher() {
        return ConstructionShare::getId;
    }
}
