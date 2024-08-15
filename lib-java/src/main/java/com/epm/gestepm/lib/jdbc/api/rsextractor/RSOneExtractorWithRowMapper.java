package com.epm.gestepm.lib.jdbc.api.rsextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BinaryOperator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public abstract class RSOneExtractorWithRowMapper<T, K> implements RSExtractorWithRowMapper<T>,
        ResultSetExtractor<T> {

    @Override
    public T extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        T data = null;

        final BinaryOperator<T> reducer = getReducer();
        final RowMapper<T> rowMapper = getRowMapper();

        while (resultSet.next()) {
            final T item = rowMapper.mapRow(resultSet, 0);
            data = data == null ? item : reducer.apply(data, item);
        }

        return data;
    }

}
