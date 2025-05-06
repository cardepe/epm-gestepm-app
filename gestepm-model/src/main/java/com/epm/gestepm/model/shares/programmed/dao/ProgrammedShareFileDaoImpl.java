package com.epm.gestepm.model.shares.programmed.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShareFile;
import com.epm.gestepm.model.shares.programmed.dao.entity.creator.ProgrammedShareFileCreate;
import com.epm.gestepm.model.shares.programmed.dao.entity.deleter.ProgrammedShareFileDelete;
import com.epm.gestepm.model.shares.programmed.dao.entity.filter.ProgrammedShareFileFilter;
import com.epm.gestepm.model.shares.programmed.dao.entity.finder.ProgrammedShareFileByIdFinder;
import com.epm.gestepm.model.shares.programmed.dao.mappers.ProgrammedShareFileRowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareFileQueries.*;

@Component("programmedShareFileDao")
@EnableExecutionLog(layerMarker = DAO)
public class ProgrammedShareFileDaoImpl implements ProgrammedShareFileDao {

    private final SQLDatasource sqlDatasource;

    public ProgrammedShareFileDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of programmed share files",
            msgOut = "Querying list of programmed share files OK",
            errorMsg = "Failed to query list of programmed share files")
    public List<ProgrammedShareFile> list(ProgrammedShareFileFilter filter) {

        final SQLQueryFetchMany<ProgrammedShareFile> sqlQuery = new SQLQueryFetchMany<ProgrammedShareFile>()
                .useRowMapper(new ProgrammedShareFileRowMapper())
                .useQuery(QRY_LIST_OF_PSF)
                .useFilter(FILTER_PSF_BY_PARAMS)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find programmed share file by ID",
            msgOut = "Querying to find programmed share file by ID OK",
            errorMsg = "Failed query to find programmed share file by ID")
    public Optional<ProgrammedShareFile> find(ProgrammedShareFileByIdFinder finder) {

        final SQLQueryFetchOne<ProgrammedShareFile> sqlQuery = new SQLQueryFetchOne<ProgrammedShareFile>()
                .useRowMapper(new ProgrammedShareFileRowMapper())
                .useQuery(QRY_LIST_OF_PSF)
                .useFilter(FILTER_PSF_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new programmed share file",
            msgOut = "New programmed share file persisted OK",
            errorMsg = "Failed to persist new programmed share file")
    public ProgrammedShareFile create(ProgrammedShareFileCreate create) {

        final AttributeMap params = create.collectAttributes();

        final ProgrammedShareFileByIdFinder finder = new ProgrammedShareFileByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_PSF)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for programmed share file",
            msgOut = "Delete for programmed share file persisted OK",
            errorMsg = "Failed to persist delete for programmed share file")
    public void delete(ProgrammedShareFileDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PSF)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }
}
