package com.epm.gestepm.lib.jdbc.api.rsextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public abstract class RSManyExtractorWithRowMapper<T, K> implements RSExtractorWithRowMapper<T>,
        ResultSetExtractor<List<T>> {

    public abstract Function<T, K> getKeyMatcher();

    @Override
    public List<T> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        final LinkedHashMap<K, T> dataMap = new LinkedHashMap<>();

        final Function<T, K> keyMatcher = getKeyMatcher();
        final BinaryOperator<T> reducer = getReducer();
        final RowMapper<T> rowMapper = getRowMapper();

        while (resultSet.next()) {

            final T item = rowMapper.mapRow(resultSet, 0);
            final K itemKey = keyMatcher.apply(item);

            dataMap.computeIfPresent(itemKey, (k, v) -> reducer.apply(v, item));
            dataMap.computeIfAbsent(itemKey, k -> item);
        }

        return new ArrayList<>(dataMap.values());
    }

}
