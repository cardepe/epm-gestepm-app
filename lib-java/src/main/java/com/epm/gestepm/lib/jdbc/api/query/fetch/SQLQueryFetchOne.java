package com.epm.gestepm.lib.jdbc.api.query.fetch;

import java.util.Map;
import java.util.Objects;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class SQLQueryFetchOne<T> extends SQLQueryFetch {

    private RowMapper<T> rowMapper;

    private ResultSetExtractor<T> rsExtractor;

    public SQLQueryFetchOne() {
        super();
    }

    @Override
    public SQLQueryFetchOne<T> useQuery(String queryKey) {
        super.useQuery(queryKey);
        return this;
    }

    @Override
    public SQLQueryFetchOne<T> useFilter(String filterKey) {
        super.useFilter(filterKey);
        return this;
    }

    @Override
    public SQLQueryFetchOne<T> withParams(Map<String, Object> params) {
        super.withParams(params);
        return this;
    }

    public SQLQueryFetchOne<T> useRowMapper(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
        this.rsExtractor = null;

        return this;
    }

    public SQLQueryFetchOne<T> useRsExtractor(ResultSetExtractor<T> rsExtractor) {
        this.rsExtractor = rsExtractor;
        this.rowMapper = null;

        return this;
    }

    public RowMapper<T> getRowMapper() {
        return rowMapper;
    }

    public ResultSetExtractor<T> getRsExtractor() {
        return rsExtractor;
    }

    @Override
    // DO NOT AUTOGENERATE
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SQLQueryFetchOne)) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    @Override
    // DO NOT AUTOGENERATE
    public int hashCode() {

        final RowMapper<T> rowMapper = getRowMapper();
        final ResultSetExtractor<T> rsExtractor = getRsExtractor();

        final String rowMapperClass = rowMapper != null ? rowMapper.getClass().getCanonicalName() : null;
        final String rsExtractorClass = rsExtractor != null ? rsExtractor.getClass().getCanonicalName() : null;

        return Objects.hash(super.hashCode(), rowMapperClass, rsExtractorClass);
    }

}
