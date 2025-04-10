package com.epm.gestepm.model.shares.displacement.dao;

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
import com.epm.gestepm.model.shares.displacement.dao.entity.DisplacementShare;
import com.epm.gestepm.model.shares.displacement.dao.entity.creator.DisplacementShareCreate;
import com.epm.gestepm.model.shares.displacement.dao.entity.deleter.DisplacementShareDelete;
import com.epm.gestepm.model.shares.displacement.dao.entity.filter.DisplacementShareFilter;
import com.epm.gestepm.model.shares.displacement.dao.entity.finder.DisplacementShareByIdFinder;
import com.epm.gestepm.model.shares.displacement.dao.entity.updater.DisplacementShareUpdate;
import com.epm.gestepm.model.shares.displacement.dao.mappers.DisplacementShareRowMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType.DESC;
import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.shares.displacement.dao.constants.DisplacementShareQueries.*;
import static com.epm.gestepm.model.shares.displacement.dao.mappers.DisplacementShareRowMapper.COL_DS_ID;

@AllArgsConstructor
@Component("displacementShareDao")
@EnableExecutionLog(layerMarker = DAO)
public class DisplacementShareDaoImpl implements DisplacementShareDao {

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of displacement shares",
            msgOut = "Querying list of displacement shares OK",
            errorMsg = "Failed to query list of displacement shares")
    public List<DisplacementShare> list(DisplacementShareFilter filter) {

        final SQLQueryFetchMany<DisplacementShare> sqlQuery = new SQLQueryFetchMany<DisplacementShare>()
                .useRowMapper(new DisplacementShareRowMapper())
                .useQuery(QRY_LIST_OF_DS)
                .useFilter(FILTER_DS_BY_PARAMS)
                .withParams(filter.collectAttributes());

        setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of displacement shares",
            msgOut = "Querying list of displacement shares OK",
            errorMsg = "Failed to query list of displacement shares")
    public Page<DisplacementShare> list(DisplacementShareFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<DisplacementShare> sqlQuery = new SQLQueryFetchPage<DisplacementShare>()
                .useRowMapper(new DisplacementShareRowMapper())
                .useQuery(QRY_PAGE_OF_DS)
                .useCountQuery(QRY_COUNT_OF_DS)
                .useFilter(FILTER_DS_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find displacement share by ID",
            msgOut = "Querying to find displacement share by ID OK",
            errorMsg = "Failed query to find displacement share by ID")
    public Optional<DisplacementShare> find(DisplacementShareByIdFinder finder) {

        final SQLQueryFetchOne<DisplacementShare> sqlQuery = new SQLQueryFetchOne<DisplacementShare>()
                .useRowMapper(new DisplacementShareRowMapper())
                .useQuery(QRY_LIST_OF_DS)
                .useFilter(FILTER_DS_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new displacement share",
            msgOut = "New displacement share persisted OK",
            errorMsg = "Failed to persist new displacement share")
    public DisplacementShare create(DisplacementShareCreate create) {

        final AttributeMap params = create.collectAttributes();

        final DisplacementShareByIdFinder finder = new DisplacementShareByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_DS)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for displacement share",
            msgOut = "Update for displacement share persisted OK",
            errorMsg = "Failed to persist update for displacement share")
    public DisplacementShare update(DisplacementShareUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final DisplacementShareByIdFinder finder = new DisplacementShareByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_DS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for displacement share",
            msgOut = "Delete for displacement share persisted OK",
            errorMsg = "Failed to persist delete for displacement share")
    public void delete(DisplacementShareDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_DS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(SQLOrderByType order, String orderBy, SQLQueryFetchMany<DisplacementShare> sqlQuery) {

        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id") ? orderBy : COL_DS_ID;
        final SQLOrderByType orderStatement = order != null ? order : DESC;

        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }
}
