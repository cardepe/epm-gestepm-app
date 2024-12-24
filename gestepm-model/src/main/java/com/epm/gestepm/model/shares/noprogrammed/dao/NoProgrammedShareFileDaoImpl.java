package com.epm.gestepm.model.shares.noprogrammed.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShareFile;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareFileCreate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFileFilter;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareFileByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.dao.mappers.NoProgrammedShareFileRowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileQueries.*;

@Component("noProgrammedShareFileDao")
@EnableExecutionLog(layerMarker = DAO)
public class NoProgrammedShareFileDaoImpl implements NoProgrammedShareFileDao {

    private final SQLDatasource sqlDatasource;

    public NoProgrammedShareFileDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of no programmed share files",
            msgOut = "Querying list of no programmed share files OK",
            errorMsg = "Failed to query list of no programmed share files")
    public List<NoProgrammedShareFile> list(NoProgrammedShareFileFilter filter) {

        final SQLQueryFetchMany<NoProgrammedShareFile> sqlQuery = new SQLQueryFetchMany<NoProgrammedShareFile>()
                .useRowMapper(new NoProgrammedShareFileRowMapper())
                .useQuery(QRY_LIST_OF_NPSF)
                .useFilter(FILTER_NPSF_BY_PARAMS)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find no programmed share file by ID",
            msgOut = "Querying to find no programmed share file by ID OK",
            errorMsg = "Failed query to find no programmed share file by ID")
    public Optional<NoProgrammedShareFile> find(NoProgrammedShareFileByIdFinder finder) {

        final SQLQueryFetchOne<NoProgrammedShareFile> sqlQuery = new SQLQueryFetchOne<NoProgrammedShareFile>()
                .useRowMapper(new NoProgrammedShareFileRowMapper())
                .useQuery(QRY_LIST_OF_NPSF)
                .useFilter(FILTER_NPSF_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new no programmed share file",
            msgOut = "New no programmed share file persisted OK",
            errorMsg = "Failed to persist new no programmed share file")
    public NoProgrammedShareFile create(NoProgrammedShareFileCreate create) {

        final AttributeMap params = create.collectAttributes();

        final NoProgrammedShareFileByIdFinder finder = new NoProgrammedShareFileByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_NPSF)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }
}
