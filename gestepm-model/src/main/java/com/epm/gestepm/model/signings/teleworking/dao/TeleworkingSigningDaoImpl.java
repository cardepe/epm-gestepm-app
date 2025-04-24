package com.epm.gestepm.model.signings.teleworking.dao;

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
import com.epm.gestepm.model.signings.teleworking.dao.entity.TeleworkingSigning;
import com.epm.gestepm.model.signings.teleworking.dao.entity.creator.TeleworkingSigningCreate;
import com.epm.gestepm.model.signings.teleworking.dao.entity.deleter.TeleworkingSigningDelete;
import com.epm.gestepm.model.signings.teleworking.dao.entity.filter.TeleworkingSigningFilter;
import com.epm.gestepm.model.signings.teleworking.dao.entity.finder.TeleworkingSigningByIdFinder;
import com.epm.gestepm.model.signings.teleworking.dao.entity.updater.TeleworkingSigningUpdate;
import com.epm.gestepm.model.signings.teleworking.dao.mappers.TeleworkingSigningRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.signings.teleworking.dao.constants.TeleworkingSigningQueries.*;
import static com.epm.gestepm.model.signings.teleworking.dao.mappers.TeleworkingSigningRowMapper.*;

@Component("teleworkingSigningDao")
@EnableExecutionLog(layerMarker = DAO)
public class TeleworkingSigningDaoImpl implements TeleworkingSigningDao {

    private final SQLDatasource sqlDatasource;

    public TeleworkingSigningDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of teleworking signings",
            msgOut = "Querying list of teleworking signings OK",
            errorMsg = "Failed to query list of teleworking signings")
    public List<TeleworkingSigning> list(TeleworkingSigningFilter filter) {

        final SQLQueryFetchMany<TeleworkingSigning> sqlQuery = new SQLQueryFetchMany<TeleworkingSigning>()
                .useRowMapper(new TeleworkingSigningRowMapper())
                .useQuery(QRY_LIST_OF_TS)
                .useFilter(FILTER_TS_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying page of teleworking signings",
            msgOut = "Querying page of teleworking signings OK",
            errorMsg = "Failed to query page of teleworking signings")
    public Page<TeleworkingSigning> list(TeleworkingSigningFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<TeleworkingSigning> sqlQuery = new SQLQueryFetchPage<TeleworkingSigning>()
                .useRowMapper(new TeleworkingSigningRowMapper())
                .useQuery(QRY_PAGE_OF_TS)
                .useCountQuery(QRY_COUNT_OF_TS)
                .useFilter(FILTER_TS_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find teleworking signing by ID",
            msgOut = "Querying to find teleworking signing by ID OK",
            errorMsg = "Failed query to find teleworking signing by ID")
    public Optional<TeleworkingSigning> find(TeleworkingSigningByIdFinder finder) {

        final SQLQueryFetchOne<TeleworkingSigning> sqlQuery = new SQLQueryFetchOne<TeleworkingSigning>()
                .useRowMapper(new TeleworkingSigningRowMapper())
                .useQuery(QRY_LIST_OF_TS)
                .useFilter(FILTER_TS_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new teleworking signing",
            msgOut = "New teleworking signing persisted OK",
            errorMsg = "Failed to persist new teleworking signing")
    public TeleworkingSigning create(TeleworkingSigningCreate create) {

        final AttributeMap params = create.collectAttributes();

        final TeleworkingSigningByIdFinder finder = new TeleworkingSigningByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_TS)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for teleworking signing",
            msgOut = "Update for teleworking signing persisted OK",
            errorMsg = "Failed to persist update for teleworking signing")
    public TeleworkingSigning update(TeleworkingSigningUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final TeleworkingSigningByIdFinder finder = new TeleworkingSigningByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_TS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for teleworking signing",
            msgOut = "Delete for teleworking signing persisted OK",
            errorMsg = "Failed to persist delete for teleworking signing")
    public void delete(TeleworkingSigningDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_TS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<TeleworkingSigning> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_TS_ID;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.DESC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("startedAt".equals(orderBy)) {
            return COL_TS_STARTED_AT;
        } else if ("closedAt".equals(orderBy)) {
            return COL_TS_CLOSED_AT;
        } else if ("project.name".equals(orderBy)) {
            return COL_TS_PROJECT_NAME;
        }
        return orderBy;
    }
}
