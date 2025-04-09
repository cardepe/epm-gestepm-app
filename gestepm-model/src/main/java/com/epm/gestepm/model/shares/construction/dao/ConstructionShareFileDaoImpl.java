package com.epm.gestepm.model.shares.construction.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShareFile;
import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareFileCreate;
import com.epm.gestepm.model.shares.construction.dao.entity.deleter.ConstructionShareFileDelete;
import com.epm.gestepm.model.shares.construction.dao.entity.filter.ConstructionShareFileFilter;
import com.epm.gestepm.model.shares.construction.dao.entity.finder.ConstructionShareFileByIdFinder;
import com.epm.gestepm.model.shares.construction.dao.mappers.ConstructionShareFileRowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileQueries.*;

@Component("constructionShareFileDao")
@EnableExecutionLog(layerMarker = DAO)
public class ConstructionShareFileDaoImpl implements ConstructionShareFileDao {

    private final SQLDatasource sqlDatasource;

    public ConstructionShareFileDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of construction share files",
            msgOut = "Querying list of construction share files OK",
            errorMsg = "Failed to query list of construction share files")
    public List<ConstructionShareFile> list(ConstructionShareFileFilter filter) {

        final SQLQueryFetchMany<ConstructionShareFile> sqlQuery = new SQLQueryFetchMany<ConstructionShareFile>()
                .useRowMapper(new ConstructionShareFileRowMapper())
                .useQuery(QRY_LIST_OF_CSF)
                .useFilter(FILTER_CSF_BY_PARAMS)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find construction share file by ID",
            msgOut = "Querying to find construction share file by ID OK",
            errorMsg = "Failed query to find construction share file by ID")
    public Optional<ConstructionShareFile> find(ConstructionShareFileByIdFinder finder) {

        final SQLQueryFetchOne<ConstructionShareFile> sqlQuery = new SQLQueryFetchOne<ConstructionShareFile>()
                .useRowMapper(new ConstructionShareFileRowMapper())
                .useQuery(QRY_LIST_OF_CSF)
                .useFilter(FILTER_CSF_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new construction share file",
            msgOut = "New construction share file persisted OK",
            errorMsg = "Failed to persist new construction share file")
    public ConstructionShareFile create(ConstructionShareFileCreate create) {

        final AttributeMap params = create.collectAttributes();

        final ConstructionShareFileByIdFinder finder = new ConstructionShareFileByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_CSF)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for construction share file",
            msgOut = "Delete for construction share file persisted OK",
            errorMsg = "Failed to persist delete for construction share file")
    public void delete(ConstructionShareFileDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_CSF)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }
}
