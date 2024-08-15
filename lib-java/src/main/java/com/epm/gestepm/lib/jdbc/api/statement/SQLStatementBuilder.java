package com.epm.gestepm.lib.jdbc.api.statement;

import com.epm.gestepm.lib.jdbc.api.query.SQLBatch;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetch;

public interface SQLStatementBuilder {

    String build(SQLQuery sqlQuery);

    String build(SQLQueryFetch sqlQueryFetch);

    String build(SQLBatch sqlQuery);

}
