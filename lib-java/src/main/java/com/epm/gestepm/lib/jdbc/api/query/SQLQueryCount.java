package com.epm.gestepm.lib.jdbc.api.query;

import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchPage;

public class SQLQueryCount extends SQLQuery {

    public SQLQueryCount of(SQLQueryFetchPage<?> sqlQueryPage) {

        final SQLQueryCount sqlQueryCount = new SQLQueryCount();
        sqlQueryCount.useQuery(sqlQueryPage.getCountQueryKey());
        sqlQueryCount.useFilter(sqlQueryPage.getFilterKey());
        sqlQueryCount.withParams(sqlQueryPage.getParams());

        return sqlQueryCount;
    }

}
