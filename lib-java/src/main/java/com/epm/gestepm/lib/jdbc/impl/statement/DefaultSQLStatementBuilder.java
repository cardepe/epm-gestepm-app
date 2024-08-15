package com.epm.gestepm.lib.jdbc.impl.statement;

import static java.util.stream.Collectors.toList;
import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.epm.gestepm.lib.jdbc.api.orderby.SQLColumnOrderBy;
import com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType;
import com.epm.gestepm.lib.jdbc.api.query.SQLBatch;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetch;
import com.epm.gestepm.lib.jdbc.api.statement.SQLStatementBuilder;
import com.epm.gestepm.lib.jdbc.api.statement.SQLStatementKeyResolver;
import com.epm.gestepm.lib.jdbc.impl.filter.SQLFilterParser;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;

@EnableExecutionLog(layerMarker = DELEGATOR)
public class DefaultSQLStatementBuilder implements SQLStatementBuilder {

    private final SQLStatementKeyResolver sqlStatementKeyResolver;

    public DefaultSQLStatementBuilder(SQLStatementKeyResolver sqlStatementKeyResolver) {
        this.sqlStatementKeyResolver = sqlStatementKeyResolver;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            debugOut = true,
            level = "TRACE",
            msgIn = "Building SQL query",
            msgOut = "SQL query build OK",
            errorMsg = "Error building SQL query")
    public String build(SQLQuery sqlQuery) {

        final String queryKey = sqlQuery.getQueryKey();
        final String filterKey = sqlQuery.getFilterKey();
        final List<String> nonNullParams = collectNonNullParams(sqlQuery);

        return buildForKey(queryKey, filterKey, new ArrayList<>(), nonNullParams);
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            debugOut = true,
            level = "TRACE",
            msgIn = "Building SQL query for data fetch",
            msgOut = "SQL query for data fetch build OK",
            errorMsg = "Error building SQL query for data fetch")
    public String build(SQLQueryFetch sqlQuery) {

        final String queryKey = sqlQuery.getQueryKey();
        final String filterKey = sqlQuery.getFilterKey();
        final List<SQLColumnOrderBy> orderBy = sqlQuery.getOrderBy();
        final List<String> nonNullParams = collectNonNullParams(sqlQuery);

        return buildForKey(queryKey, filterKey, orderBy, nonNullParams);
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            debugOut = true,
            level = "TRACE",
            msgIn = "Building SQL batch query",
            msgOut = "SQL batch query build OK",
            errorMsg = "Error building SQL batch query")
    public String build(SQLBatch sqlQuery) {

        final String queryKey = sqlQuery.getQueryKey();

        // TODO analyze the #filter possibility

        return buildForKey(queryKey, null, new ArrayList<>(), new ArrayList<>());
    }

    private List<String> collectNonNullParams(final SQLQuery sqlQuery) {
        final Map<String, Object> params = sqlQuery.getParams();
        return params.entrySet().stream().filter(e -> e.getValue() != null).map(Entry::getKey).collect(toList());
    }

    private String buildForKey(final String queryKey, final String filterKey, final List<SQLColumnOrderBy> orderBy,
            final List<String> paramsToKeep) {

        String statement = sqlStatementKeyResolver.resolve(queryKey);
        String filter;
        String order;

        if (statement.contains("#filter") && filterKey != null) {

            filter = sqlStatementKeyResolver.resolve(filterKey);
            filter = SQLFilterParser.parse(filter, paramsToKeep);

            statement = statement.replace("#filter", filter);

            if (!filter.isBlank()) {
                statement = statement.replace("#whereToken", "WHERE ");
            }
        }

        if (statement.contains("#orderBy") && !orderBy.isEmpty()) {

            order = orderBy.stream().map(ob -> {

                final String columnName = ob.getColumnName();
                final SQLOrderByType type = ob.getSqlOrderByType();

                return String.format("%s %s", columnName, type.toString());

            }).collect(Collectors.joining(","));

            if (!order.isBlank()) {
                statement = statement.replace("#orderByToken", "ORDER BY #orderBy");
            }

            statement = statement.replace("#orderBy", order);
        }

        statement = statement.replace("#filter", "");
        statement = statement.replace("#whereToken", "");
        statement = statement.replace("#orderByToken", "");

        return statement;
    }

}
