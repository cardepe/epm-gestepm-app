package com.epm.gestepm.lib.jdbc.api.rsextractor;

import java.util.function.BinaryOperator;
import org.springframework.jdbc.core.RowMapper;

public interface RSExtractorWithRowMapper<T> {

    RowMapper<T> getRowMapper();

    BinaryOperator<T> getReducer();

}
