package com.epm.gestepm.lib.jdbc.api.datasource;

import java.util.List;
import java.util.Optional;
import com.epm.gestepm.lib.jdbc.api.query.SQLBatch;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.SQLQueryCount;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchPage;
import com.epm.gestepm.lib.types.Page;

public interface SQLDatasource {

    <T> Optional<T> fetch(final SQLQueryFetchOne<T> sqlQuery);

    <T> List<T> fetch(final SQLQueryFetchMany<T> sqlQuery);

    <T> Page<T> fetch(final SQLQueryFetchPage<T> sqlQuery);

    Long count(final SQLQueryCount sqlQuery);

    <K> void insert(final SQLInsert<K> sqlInsert);

    void batch(final SQLBatch sqlBatch);

    void execute(final SQLQuery sqlQuery);

}
