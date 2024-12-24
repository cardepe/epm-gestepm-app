package com.epm.gestepm.model.inspection.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.inspection.dao.entity.InspectionFile;
import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionFileCreate;
import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFileFilter;
import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionFileByIdFinder;
import com.epm.gestepm.model.inspection.dao.mappers.InspectionFileRowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import static com.epm.gestepm.model.inspection.dao.constants.InspectionFileQueries.*;

@Component("inspectionFileDao")
@EnableExecutionLog(layerMarker = DAO)
public class InspectionFileDaoImpl implements InspectionFileDao {

    private final SQLDatasource sqlDatasource;

    public InspectionFileDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of inspection files",
            msgOut = "Querying list of inspection files OK",
            errorMsg = "Failed to query list of inspection files")
    public List<InspectionFile> list(InspectionFileFilter filter) {

        final SQLQueryFetchMany<InspectionFile> sqlQuery = new SQLQueryFetchMany<InspectionFile>()
                .useRowMapper(new InspectionFileRowMapper())
                .useQuery(QRY_LIST_OF_IF)
                .useFilter(FILTER_IF_BY_PARAMS)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find inspection file by ID",
            msgOut = "Querying to find inspection file by ID OK",
            errorMsg = "Failed query to find inspection file by ID")
    public Optional<InspectionFile> find(InspectionFileByIdFinder finder) {

        final SQLQueryFetchOne<InspectionFile> sqlQuery = new SQLQueryFetchOne<InspectionFile>()
                .useRowMapper(new InspectionFileRowMapper())
                .useQuery(QRY_LIST_OF_IF)
                .useFilter(FILTER_IF_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new inspection file",
            msgOut = "New inspection file persisted OK",
            errorMsg = "Failed to persist new inspection file")
    public InspectionFile create(InspectionFileCreate create) {

        final AttributeMap params = create.collectAttributes();

        final InspectionFileByIdFinder finder = new InspectionFileByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_IF)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }
}
