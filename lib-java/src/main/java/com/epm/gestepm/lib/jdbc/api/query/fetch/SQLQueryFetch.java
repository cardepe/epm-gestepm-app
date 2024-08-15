package com.epm.gestepm.lib.jdbc.api.query.fetch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.epm.gestepm.lib.jdbc.api.orderby.SQLColumnOrderBy;
import com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;

public class SQLQueryFetch extends SQLQuery {

    private final List<SQLColumnOrderBy> orderBy;

    public SQLQueryFetch() {
        super();
        this.orderBy = new ArrayList<>();
    }

    @Override
    public SQLQueryFetch useQuery(String queryKey) {
        super.useQuery(queryKey);
        return this;
    }

    @Override
    public SQLQueryFetch useFilter(String filterKey) {
        super.useFilter(filterKey);
        return this;
    }

    @Override
    public SQLQueryFetch withParams(Map<String, Object> params) {
        super.withParams(params);
        return this;
    }

    public SQLQueryFetch addOrderBy(final String columnName) {
        this.orderBy.add(new SQLColumnOrderBy(columnName));
        return this;
    }

    public SQLQueryFetch addOrderBy(final String columnName, final SQLOrderByType type) {
        this.orderBy.add(new SQLColumnOrderBy(columnName, type));
        return this;
    }

    public List<SQLColumnOrderBy> getOrderBy() {
        return orderBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SQLQueryFetch)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SQLQueryFetch that = (SQLQueryFetch) o;
        return Objects.equals(getOrderBy(), that.getOrderBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOrderBy());
    }

}
