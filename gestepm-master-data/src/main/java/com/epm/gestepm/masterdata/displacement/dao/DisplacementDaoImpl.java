package com.epm.gestepm.masterdata.displacement.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchPage;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.displacement.dao.entity.Displacement;
import com.epm.gestepm.masterdata.displacement.dao.entity.creator.DisplacementCreate;
import com.epm.gestepm.masterdata.displacement.dao.entity.deleter.DisplacementDelete;
import com.epm.gestepm.masterdata.displacement.dao.entity.filter.DisplacementFilter;
import com.epm.gestepm.masterdata.displacement.dao.entity.finder.DisplacementByIdFinder;
import com.epm.gestepm.masterdata.displacement.dao.entity.updater.DisplacementUpdate;
import com.epm.gestepm.masterdata.displacement.dao.mappers.DisplacementRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType.ASC;
import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.masterdata.displacement.dao.constants.DisplacementQueries.*;
import static com.epm.gestepm.masterdata.displacement.dao.mappers.DisplacementRowMapper.COL_D_ID;

@Component("displacementDao")
@EnableExecutionLog(layerMarker = DAO)
public class DisplacementDaoImpl implements DisplacementDao {

    private final SQLDatasource sqlDatasource;

    public DisplacementDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of displacements",
            msgOut = "Querying list of displacements OK",
            errorMsg = "Failed to query list of displacements")
    public List<Displacement> list(DisplacementFilter filter) {

        final SQLQueryFetchMany<Displacement> sqlQuery = new SQLQueryFetchMany<Displacement>()
                .useRowMapper(new DisplacementRowMapper())
                .useQuery(QRY_LIST_OF_D)
                .useFilter(FILTER_D_BY_PARAMS)
                .withParams(filter.collectAttributes());

        setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying page of displacements",
            msgOut = "Querying page of displacements OK",
            errorMsg = "Failed to query page of displacements")
    public Page<Displacement> list(DisplacementFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<Displacement> sqlQuery = new SQLQueryFetchPage<Displacement>()
                .useRowMapper(new DisplacementRowMapper())
                .useQuery(QRY_PAGE_OF_D)
                .useCountQuery(QRY_COUNT_OF_D)
                .useFilter(FILTER_D_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find displacement by ID",
            msgOut = "Querying to find displacement by ID OK",
            errorMsg = "Failed query to find displacement by ID")
    public Optional<Displacement> find(DisplacementByIdFinder finder) {

        final SQLQueryFetchOne<Displacement> sqlQuery = new SQLQueryFetchOne<Displacement>()
                .useRowMapper(new DisplacementRowMapper())
                .useQuery(QRY_LIST_OF_D)
                .useFilter(FILTER_D_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new displacement",
            msgOut = "New displacement persisted OK",
            errorMsg = "Failed to persist new displacement")
    public Displacement create(DisplacementCreate create) {

        final AttributeMap params = create.collectAttributes();

        final DisplacementByIdFinder finder = new DisplacementByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_D)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for displacement",
            msgOut = "Update for displacement persisted OK",
            errorMsg = "Failed to persist update for displacement")
    public Displacement update(DisplacementUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final DisplacementByIdFinder finder = new DisplacementByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_D)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for displacement",
            msgOut = "Delete for displacement persisted OK",
            errorMsg = "Failed to persist delete for displacement")
    public void delete(DisplacementDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_D)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(SQLOrderByType order, String orderBy, SQLQueryFetchMany<Displacement> sqlQuery) {

        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id") ? orderBy : COL_D_ID;
        final SQLOrderByType orderStatement = order != null ? order : ASC;

        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }
}
