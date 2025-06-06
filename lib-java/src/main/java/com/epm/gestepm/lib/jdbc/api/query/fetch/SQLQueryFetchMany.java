package com.epm.gestepm.lib.jdbc.api.query.fetch;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class SQLQueryFetchMany<T> extends SQLQueryFetch {

    private RowMapper<T> rowMapper;

    private ResultSetExtractor<List<T>> rsExtractor;

    public SQLQueryFetchMany() {
        super();
    }

    public SQLQueryFetchMany<T> useRowMapper(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
        this.rsExtractor = null;

        return this;
    }

    public SQLQueryFetchMany<T> useRsExtractor(ResultSetExtractor<List<T>> rsExtractor) {
        this.rsExtractor = rsExtractor;
        this.rowMapper = null;

        return this;
    }

    public RowMapper<T> getRowMapper() {
        return rowMapper;
    }

    public ResultSetExtractor<List<T>> getRsExtractor() {
        return rsExtractor;
    }

    @Override
    public SQLQueryFetchMany<T> useQuery(String queryKey) {
        super.useQuery(queryKey);
        return this;
    }

    @Override
    public SQLQueryFetchMany<T> useFilter(String filterKey) {
        super.useFilter(filterKey);
        return this;
    }

    @Override
    public SQLQueryFetchMany<T> withParams(Map<String, Object> params) {
        super.withParams(params);
        return this;
    }

    @Override
    public SQLQueryFetchMany<T> addOrderBy(String columnName) {
        super.addOrderBy(columnName);
        return this;
    }

    @Override
    public SQLQueryFetchMany<T> addOrderBy(String columnName, SQLOrderByType type) {
        super.addOrderBy(columnName, type);
        return this;
    }

    @Override
    public SQLQueryFetchMany<T> addOrderBy(List<String> columnNames, SQLOrderByType type) {
        super.addOrderBy(columnNames, type);
        return this;
    }

    @Override
    // DO NOT AUTOGENERATE
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SQLQueryFetchMany)) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    @Override
    // DO NOT AUTOGENERATE
    public int hashCode() {

        final RowMapper<T> rowMapper = getRowMapper();
        final ResultSetExtractor<List<T>> rsExtractor = getRsExtractor();

        final String rowMapperClass = rowMapper != null ? rowMapper.getClass().getCanonicalName() : null;
        final String rsExtractorClass = rsExtractor != null ? rsExtractor.getClass().getCanonicalName() : null;

        return Objects.hash(super.hashCode(), rowMapperClass, rsExtractorClass);
    }

}
