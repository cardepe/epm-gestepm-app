package com.epm.gestepm.lib.jdbc.api.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SQLQuery {

    private final Map<String, Object> params;

    private String queryKey;

    private String filterKey;

    public SQLQuery() {
        this.params = new HashMap<>();
    }

    public SQLQuery useQuery(final String queryKey) {
        this.queryKey = queryKey;
        return this;
    }

    public SQLQuery useFilter(final String filterKey) {
        this.filterKey = filterKey;
        return this;
    }

    public SQLQuery withParams(Map<String, Object> params) {
        this.params.clear();
        this.params.putAll(Objects.requireNonNullElseGet(params, HashMap::new));
        return this;
    }

    public String getQueryKey() {
        return queryKey;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public Map<String, Object> getParams() {
        return new HashMap<>(params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SQLQuery)) {
            return false;
        }
        SQLQuery sqlQuery = (SQLQuery) o;
        return Objects.equals(getQueryKey(), sqlQuery.getQueryKey())
                && Objects
                    .equals(getFilterKey(), sqlQuery.getFilterKey())
                && Objects.equals(getParams(), sqlQuery.getParams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQueryKey(), getFilterKey(), getParams());
    }

}
