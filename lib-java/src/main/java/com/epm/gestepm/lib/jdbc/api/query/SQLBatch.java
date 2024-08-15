package com.epm.gestepm.lib.jdbc.api.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SQLBatch {

    private final List<Map<String, Object>> params;

    private String queryKey;

    private String filterKey;

    public SQLBatch() {
        this.params = new ArrayList<>();
    }

    public SQLBatch useQuery(final String queryKey) {
        this.queryKey = queryKey;
        return this;
    }

    public SQLBatch useFilter(final String filterKey) {
        this.filterKey = filterKey;
        return this;
    }

    public SQLBatch addBatchParams(Map<String, Object> params) {

        if (params != null) {
            this.params.add(params);
        }
        return this;
    }

    public String getQueryKey() {
        return queryKey;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SQLBatch)) {
            return false;
        }
        SQLBatch sqlBatch = (SQLBatch) o;
        return Objects.equals(getQueryKey(), sqlBatch.getQueryKey())
                && Objects
                    .equals(getFilterKey(), sqlBatch.getFilterKey())
                && Objects.equals(getParams(), sqlBatch.getParams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQueryKey(), getFilterKey(), getParams());
    }

}
