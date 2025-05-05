package com.epm.gestepm.model.shares.work.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.shares.work.dao.entity.WorkShareFile;
import com.epm.gestepm.model.shares.work.dao.entity.creator.WorkShareFileCreate;
import com.epm.gestepm.model.shares.work.dao.entity.deleter.WorkShareFileDelete;
import com.epm.gestepm.model.shares.work.dao.entity.filter.WorkShareFileFilter;
import com.epm.gestepm.model.shares.work.dao.entity.finder.WorkShareFileByIdFinder;
import com.epm.gestepm.model.shares.work.dao.mappers.WorkShareFileRowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.shares.work.dao.constants.WorkShareFileQueries.*;

@Component("workShareFileDao")
@EnableExecutionLog(layerMarker = DAO)
public class WorkShareFileDaoImpl implements WorkShareFileDao {

    private final SQLDatasource sqlDatasource;

    public WorkShareFileDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of work share files",
            msgOut = "Querying list of work share files OK",
            errorMsg = "Failed to query list of work share files")
    public List<WorkShareFile> list(WorkShareFileFilter filter) {

        final SQLQueryFetchMany<WorkShareFile> sqlQuery = new SQLQueryFetchMany<WorkShareFile>()
                .useRowMapper(new WorkShareFileRowMapper())
                .useQuery(QRY_LIST_OF_WSF)
                .useFilter(FILTER_WSF_BY_PARAMS)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find work share file by ID",
            msgOut = "Querying to find work share file by ID OK",
            errorMsg = "Failed query to find work share file by ID")
    public Optional<WorkShareFile> find(WorkShareFileByIdFinder finder) {

        final SQLQueryFetchOne<WorkShareFile> sqlQuery = new SQLQueryFetchOne<WorkShareFile>()
                .useRowMapper(new WorkShareFileRowMapper())
                .useQuery(QRY_LIST_OF_WSF)
                .useFilter(FILTER_WSF_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new work share file",
            msgOut = "New work share file persisted OK",
            errorMsg = "Failed to persist new work share file")
    public WorkShareFile create(WorkShareFileCreate create) {

        final AttributeMap params = create.collectAttributes();

        final WorkShareFileByIdFinder finder = new WorkShareFileByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_WSF)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for work share file",
            msgOut = "Delete for work share file persisted OK",
            errorMsg = "Failed to persist delete for work share file")
    public void delete(WorkShareFileDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_WSF)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }
}
