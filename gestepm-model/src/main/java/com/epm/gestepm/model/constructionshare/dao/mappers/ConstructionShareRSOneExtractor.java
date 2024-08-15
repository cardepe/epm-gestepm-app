package com.epm.gestepm.model.constructionshare.dao.mappers;

import com.epm.gestepm.lib.jdbc.api.rsextractor.RSOneExtractorWithRowMapper;
import com.epm.gestepm.model.constructionshare.dao.entity.ConstructionShare;
import org.springframework.jdbc.core.RowMapper;

import java.util.function.BinaryOperator;

public class ConstructionShareRSOneExtractor extends RSOneExtractorWithRowMapper<ConstructionShare, Integer> {

    @Override
    public RowMapper<ConstructionShare> getRowMapper() {
        return new ConstructionShareRowMapper();
    }

    @Override
    public BinaryOperator<ConstructionShare> getReducer() {
        return new ConstructionShareRSReducer();
    }

}
