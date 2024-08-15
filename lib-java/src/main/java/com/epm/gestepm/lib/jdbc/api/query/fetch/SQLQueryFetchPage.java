package com.epm.gestepm.lib.jdbc.api.query.fetch;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class SQLQueryFetchPage<T> extends SQLQueryFetchMany<T> {

    private Long limit;

    private Long offset;

    private String countQueryKey;

    public SQLQueryFetchPage() {
        super();
    }

    public SQLQueryFetchPage<T> useCountQuery(final String countQueryKey) {
        this.countQueryKey = countQueryKey;
        return this;
    }

    public SQLQueryFetchPage<T> limit(final Long limit) {
        this.limit = limit;
        return this;
    }

    public SQLQueryFetchPage<T> offset(final Long offset) {
        this.offset = offset;
        return this;
    }

    public Long getLimit() {
        return this.limit;
    }

    public Long getOffset() {
        return this.offset;
    }

    public String getCountQueryKey() {
        return this.countQueryKey;
    }

    @Override
    public SQLQueryFetchPage<T> useQuery(final String queryKey) {
        super.useQuery(queryKey);
        return this;
    }

    @Override
    public SQLQueryFetchPage<T> useFilter(final String filterKey) {
        super.useFilter(filterKey);
        return this;
    }

    @Override
    public SQLQueryFetchPage<T> withParams(final Map<String, Object> params) {
        super.withParams(params);
        return this;
    }

    @Override
    public SQLQueryFetchPage<T> addOrderBy(final String columnName) {
        super.addOrderBy(columnName);
        return this;
    }

    @Override
    public SQLQueryFetchPage<T> addOrderBy(final String columnName, final SQLOrderByType type) {
        super.addOrderBy(columnName, type);
        return this;
    }

    @Override
    public SQLQueryFetchPage<T> useRowMapper(final RowMapper<T> rowMapper) {
        super.useRowMapper(rowMapper);
        return this;
    }

    @Override
    public SQLQueryFetchPage<T> useRsExtractor(final ResultSetExtractor<List<T>> rsExtractor) {
        super.useRsExtractor(rsExtractor);
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = (prime * result) + Objects.hash(this.countQueryKey, this.limit, this.offset);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof SQLQueryFetchPage)) {
            return false;
        }
        final SQLQueryFetchPage other = (SQLQueryFetchPage) obj;
        return Objects.equals(this.countQueryKey, other.countQueryKey)
                && Objects.equals(this.limit, other.limit)
                && Objects.equals(this.offset, other.offset);
    }

}
