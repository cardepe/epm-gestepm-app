package com.epm.gestepm.model.shares.breaks.dao;

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
import com.epm.gestepm.model.shares.breaks.dao.entity.ShareBreak;
import com.epm.gestepm.model.shares.breaks.dao.entity.creator.ShareBreakCreate;
import com.epm.gestepm.model.shares.breaks.dao.entity.deleter.ShareBreakDelete;
import com.epm.gestepm.model.shares.breaks.dao.entity.filter.ShareBreakFilter;
import com.epm.gestepm.model.shares.breaks.dao.entity.finder.ShareBreakByIdFinder;
import com.epm.gestepm.model.shares.breaks.dao.entity.updater.ShareBreakUpdate;
import com.epm.gestepm.model.shares.breaks.dao.mappers.ShareBreakRowMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.shares.breaks.dao.constants.ShareBreakQueries.*;
import static com.epm.gestepm.model.shares.breaks.dao.mappers.ShareBreakRowMapper.*;

@AllArgsConstructor
@Component("shareBreakDao")
@EnableExecutionLog(layerMarker = DAO)
public class ShareBreakDaoImpl implements ShareBreakDao {

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of share breaks",
            msgOut = "Querying list of share breaks OK",
            errorMsg = "Failed to query list of share breaks")
    public List<ShareBreak> list(ShareBreakFilter filter) {

        final SQLQueryFetchMany<ShareBreak> sqlQuery = new SQLQueryFetchMany<ShareBreak>()
                .useRowMapper(new ShareBreakRowMapper())
                .useQuery(QRY_LIST_OF_SB)
                .useFilter(FILTER_SB_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of share breaks",
            msgOut = "Querying list of share breaks OK",
            errorMsg = "Failed to query list of share breaks")
    public Page<ShareBreak> list(ShareBreakFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<ShareBreak> sqlQuery = new SQLQueryFetchPage<ShareBreak>()
                .useRowMapper(new ShareBreakRowMapper())
                .useQuery(QRY_PAGE_OF_SB)
                .useCountQuery(QRY_COUNT_OF_SB)
                .useFilter(FILTER_SB_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find share break by ID",
            msgOut = "Querying to find share break by ID OK",
            errorMsg = "Failed query to find share break by ID")
    public Optional<ShareBreak> find(ShareBreakByIdFinder finder) {

        final SQLQueryFetchOne<ShareBreak> sqlQuery = new SQLQueryFetchOne<ShareBreak>()
                .useRowMapper(new ShareBreakRowMapper())
                .useQuery(QRY_LIST_OF_SB)
                .useFilter(FILTER_SB_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new share break",
            msgOut = "New share break persisted OK",
            errorMsg = "Failed to persist new share break")
    public ShareBreak create(ShareBreakCreate create) {

        final AttributeMap params = create.collectAttributes();

        final ShareBreakByIdFinder finder = new ShareBreakByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_SB)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for share break",
            msgOut = "Update for share break persisted OK",
            errorMsg = "Failed to persist update for share break")
    public ShareBreak update(ShareBreakUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final ShareBreakByIdFinder finder = new ShareBreakByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_SB)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for share break",
            msgOut = "Delete for share break persisted OK",
            errorMsg = "Failed to persist delete for share break")
    public void delete(ShareBreakDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_SB)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<ShareBreak> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_SB_ID;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.DESC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("startDate".equals(orderBy)) {
            return COL_SB_START_DATE;
        } else if ("endDate".equals(orderBy)) {
            return COL_SB_END_DATE;
        }
        return orderBy;
    }
}
