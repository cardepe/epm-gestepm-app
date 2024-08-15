package com.epm.gestepm.lib.jdbc.impl.datasource;

import static java.util.stream.Collectors.toList;
import static com.epm.gestepm.lib.jdbc.impl.QueryAttributes.FILTER_PAGE_LIMIT;
import static com.epm.gestepm.lib.jdbc.impl.QueryAttributes.FILTER_PAGE_OFFSET;
import static com.epm.gestepm.lib.jdbc.impl.logging.SQLDatasourceLogDataKeys.SQL_DATASOURCE_EXEC_PARAMS;
import static com.epm.gestepm.lib.jdbc.impl.logging.SQLDatasourceLogDataKeys.SQL_DATASOURCE_EXEC_STATEMENT;
import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DATASOURCE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_LOG_DUMP;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.exception.ExpectedOneButGotManyException;
import com.epm.gestepm.lib.jdbc.api.query.SQLBatch;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.SQLQueryCount;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchPage;
import com.epm.gestepm.lib.jdbc.api.statement.SQLStatementBuilder;
import com.epm.gestepm.lib.jdbc.impl.keyholder.SQLKeyHolderGenerator;
import com.epm.gestepm.lib.logging.AppLogger;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.types.Page;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

@EnableExecutionLog(layerMarker = DATASOURCE)
public class DefaultSQLDatasource implements SQLDatasource {

    private static final AppLogger logger = AppLogger.forClass(DefaultSQLDatasource.class);

    private final SQLStatementBuilder sqlStatementBuilder;

    private final NamedParameterJdbcTemplate jdbc;

    private final SQLKeyHolderGenerator sqlKeyHolderGenerator;

    public DefaultSQLDatasource(final SQLStatementBuilder sqlStatementBuilder,
            final NamedParameterJdbcTemplate jdbc,
            final SQLKeyHolderGenerator sqlKeyHolderGenerator) {
        this.sqlStatementBuilder = sqlStatementBuilder;
        this.jdbc = jdbc;
        this.sqlKeyHolderGenerator = sqlKeyHolderGenerator;
    }

    @Override
    @LogExecution(operation = OP_READ,
            msgIn = "Fetching data from database, expected ONE result",
            msgOut = "Data fetched from database",
            errorMsg = "Error fetching data from database")
    public <T> Optional<T> fetch(final SQLQueryFetchOne<T> sqlQueryFetch) {

        final String statement = this.sqlStatementBuilder.build(sqlQueryFetch);
        final MapSqlParameterSource params = this.makeParams(sqlQueryFetch);
        final RowMapper<T> rowMapper = sqlQueryFetch.getRowMapper();
        final ResultSetExtractor<T> rsExtractor = sqlQueryFetch.getRsExtractor();

        logger.handler("SQL Query to execute (select)")
            .operation(OP_LOG_DUMP)
            .data(SQL_DATASOURCE_EXEC_STATEMENT, statement)
            .data(SQL_DATASOURCE_EXEC_PARAMS, params.getValues())
            .debug();

        Optional<T> result = Optional.empty();

        if (rowMapper != null) {
            result = this.queryOne(statement, params, rowMapper);
        } else if (rsExtractor != null) {
            result = this.queryOne(statement, params, rsExtractor);
        }

        return result;
    }

    @Override
    @LogExecution(operation = OP_READ,
            msgIn = "Fetching data from database, expected LIST of results",
            msgOut = "Data fetched from database",
            errorMsg = "Error fetching data from database")
    public <T> List<T> fetch(final SQLQueryFetchMany<T> sqlQueryFetch) {

        final String statement = this.sqlStatementBuilder.build(sqlQueryFetch);
        final MapSqlParameterSource params = this.makeParams(sqlQueryFetch);
        final RowMapper<T> rowMapper = sqlQueryFetch.getRowMapper();
        final ResultSetExtractor<List<T>> rsExtractor = sqlQueryFetch.getRsExtractor();

        logger.handler("SQL Query to execute (select)")
            .operation(OP_LOG_DUMP)
            .data(SQL_DATASOURCE_EXEC_STATEMENT, statement)
            .data(SQL_DATASOURCE_EXEC_PARAMS, params.getValues())
            .debug();

        List<T> result = new ArrayList<>();

        if (rowMapper != null) {
            result = this.queryMany(statement, params, rowMapper);
        } else if (rsExtractor != null) {
            result = this.queryMany(statement, params, rsExtractor);
        }

        return result;
    }

    @Override
    @LogExecution(operation = OP_READ,
            msgIn = "Fetching data from database, expected paginated LIST of results",
            msgOut = "Data fetched from database",
            errorMsg = "Error fetching data from database")
    public <T> Page<T> fetch(final SQLQueryFetchPage<T> sqlQueryFetch) {

        final String statement = this.sqlStatementBuilder.build(sqlQueryFetch);
        final MapSqlParameterSource params = this.makeParams(sqlQueryFetch);
        final RowMapper<T> rowMapper = sqlQueryFetch.getRowMapper();
        final ResultSetExtractor<List<T>> rsExtractor = sqlQueryFetch.getRsExtractor();

        logger.handler("SQL Query to execute (paginated select)")
            .operation(OP_LOG_DUMP)
            .data(SQL_DATASOURCE_EXEC_STATEMENT, statement)
            .data(SQL_DATASOURCE_EXEC_PARAMS, params.getValues())
            .debug();

        final SQLQueryCount countQuery = new SQLQueryCount().of(sqlQueryFetch);
        final Long total = this.count(countQuery);
        List<T> data = new ArrayList<>();

        if (total > 0L && rowMapper != null) {
            data = this.queryMany(statement, params, rowMapper);
        } else if (total > 0L && rsExtractor != null) {
            data = this.queryMany(statement, params, rsExtractor);
        }

        final Page<T> result = new Page<>();
        result.setLimit(sqlQueryFetch.getLimit());
        result.setOffset(sqlQueryFetch.getOffset());
        result.setTotal(total);
        result.setContent(data);

        return result;
    }

    @Override
    @LogExecution(operation = OP_READ,
            msgIn = "Counting data from database",
            msgOut = "Data counted from database",
            errorMsg = "Error counting data from database")
    public Long count(final SQLQueryCount sqlQueryCount) {

        final String statement = this.sqlStatementBuilder.build(sqlQueryCount);
        final MapSqlParameterSource params = this.makeParams(sqlQueryCount);

        logger.handler("SQL Query to execute (select count)")
            .operation(OP_LOG_DUMP)
            .data(SQL_DATASOURCE_EXEC_STATEMENT, statement)
            .data(SQL_DATASOURCE_EXEC_PARAMS, params.getValues())
            .debug();

        final Long count = this.jdbc.queryForObject(statement, params, Long.class);

        return Objects.requireNonNullElse(count, 0L);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            msgIn = "Inserting data into database",
            msgOut = "Data inserted into database",
            errorMsg = "Error inserting data into database")
    public <K> void insert(final SQLInsert<K> sqlInsert) {

        final GeneratedKeyHolder generatedKeyHolder = this.sqlKeyHolderGenerator.get();
        final String statement = this.sqlStatementBuilder.build(sqlInsert);
        final MapSqlParameterSource params = this.makeParams(sqlInsert);
        final Consumer<K> keyConsumer = sqlInsert.getKeyConsumer();

        logger.handler("SQL Query to execute (insert)")
            .operation(OP_LOG_DUMP)
            .data(SQL_DATASOURCE_EXEC_STATEMENT, statement)
            .data(SQL_DATASOURCE_EXEC_PARAMS, params.getValues())
            .debug();

        this.jdbc.update(statement, params, generatedKeyHolder);

        keyConsumer.accept((K) generatedKeyHolder.getKey());
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Executing batch of SQL statements in database",
            msgOut = "Batch of SQL statements executed in database",
            errorMsg = "Error executing batch of SQL statements in database")
    public void batch(final SQLBatch sqlBatch) {

        final String statement = this.sqlStatementBuilder.build(sqlBatch);
        final List<MapSqlParameterSource> params = this.makeParams(sqlBatch);

        logger.handler("SQL Query to execute in batch (insert | update | delete)")
            .operation(OP_LOG_DUMP)
            .data(SQL_DATASOURCE_EXEC_STATEMENT, statement)
            .data(SQL_DATASOURCE_EXEC_PARAMS, params.stream().map(MapSqlParameterSource::getValues).collect(toList()))
            .debug();

        this.jdbc.batchUpdate(statement, params.toArray(new MapSqlParameterSource[0]));
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Executing SQL statement in database",
            msgOut = "SQL statement executed in database",
            errorMsg = "Error executing SQL statement in database")
    public void execute(final SQLQuery sqlQuery) {

        final String statement = this.sqlStatementBuilder.build(sqlQuery);
        final MapSqlParameterSource params = this.makeParams(sqlQuery);

        logger.handler("SQL Query to execute (insert | update | delete)")
            .operation(OP_LOG_DUMP)
            .data(SQL_DATASOURCE_EXEC_STATEMENT, statement)
            .data(SQL_DATASOURCE_EXEC_PARAMS, params.getValues())
            .debug();

        this.jdbc.update(statement, params);
    }

    private MapSqlParameterSource makeParams(final SQLQuery sqlQuery) {

        final Map<String, Object> paramsMap = sqlQuery.getParams();
        final Map<String, Object> map = Objects.requireNonNullElseGet(paramsMap, HashMap::new);

        return new MapSqlParameterSource(map);
    }

    private List<MapSqlParameterSource> makeParams(final SQLBatch sqlBatch) {

        final List<Map<String, Object>> paramsMap = sqlBatch.getParams();

        return paramsMap.stream()
            .filter(Objects::nonNull)
            .map(MapSqlParameterSource::new)
            .collect(toList());
    }

    private <T> MapSqlParameterSource makeParams(final SQLQueryFetchPage<T> sqlQuery) {

        final Map<String, Object> paramsMap = sqlQuery.getParams();
        final Map<String, Object> map = Objects.requireNonNullElseGet(paramsMap, HashMap::new);

        map.put(FILTER_PAGE_OFFSET, sqlQuery.getOffset());
        map.put(FILTER_PAGE_LIMIT, sqlQuery.getLimit());

        return new MapSqlParameterSource(map);
    }

    private <T> Optional<T> queryOne(final String statement, final MapSqlParameterSource params,
            final RowMapper<T> rowMapper) {

        final List<T> data = this.jdbc.query(statement, params, rowMapper);

        return this.listToOne(data);
    }

    private <T> Optional<T> queryOne(final String statement, final MapSqlParameterSource params,
            final ResultSetExtractor<T> rsExtractor) {

        final T data = this.jdbc.query(statement, params, rsExtractor);

        return Optional.ofNullable(data);
    }

    private <T> List<T> queryMany(final String statement, final MapSqlParameterSource params,
            final RowMapper<T> rowMapper) {

        return this.jdbc.query(statement, params, rowMapper);
    }

    private <T> List<T> queryMany(final String statement, final MapSqlParameterSource params,
            final ResultSetExtractor<List<T>> rsExtractor) {

        List<T> data = this.jdbc.query(statement, params, rsExtractor);
        data = Objects.requireNonNullElseGet(data, ArrayList::new);

        return data;
    }

    private <T> Optional<T> listToOne(final List<T> list) {

        final List<T> data = Objects.requireNonNullElseGet(list, ArrayList::new);
        Optional<T> result = Optional.empty();

        if (data.size() == 1) {

            final T value = data.get(0);
            result = Optional.of(value);

        } else if (data.size() > 1) {
            throw new ExpectedOneButGotManyException();
        }

        return result;
    }

}
