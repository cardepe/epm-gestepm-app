package com.epm.gestepm.masterdata.holiday.dao;

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
import com.epm.gestepm.masterdata.holiday.dao.entity.Holiday;
import com.epm.gestepm.masterdata.holiday.dao.entity.creator.HolidayCreate;
import com.epm.gestepm.masterdata.holiday.dao.entity.deleter.HolidayDelete;
import com.epm.gestepm.masterdata.holiday.dao.entity.filter.HolidayFilter;
import com.epm.gestepm.masterdata.holiday.dao.entity.finder.HolidayByIdFinder;
import com.epm.gestepm.masterdata.holiday.dao.entity.updater.HolidayUpdate;
import com.epm.gestepm.masterdata.holiday.dao.mappers.HolidayRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType.ASC;
import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.masterdata.holiday.dao.constants.HolidayQueries.*;
import static com.epm.gestepm.masterdata.holiday.dao.mappers.HolidayRowMapper.COL_H_ID;

@Component("holidayDao")
@EnableExecutionLog(layerMarker = DAO)
public class HolidayDaoImpl implements HolidayDao {

    private final SQLDatasource sqlDatasource;

    public HolidayDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of holidays",
            msgOut = "Querying list of holidays OK",
            errorMsg = "Failed to query list of holidays")
    public List<Holiday> list(HolidayFilter filter) {

        final SQLQueryFetchMany<Holiday> sqlQuery = new SQLQueryFetchMany<Holiday>()
                .useRowMapper(new HolidayRowMapper())
                .useQuery(QRY_LIST_OF_H)
                .useFilter(FILTER_H_BY_PARAMS)
                .withParams(filter.collectAttributes());

        setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying page of holidays",
            msgOut = "Querying page of holidays OK",
            errorMsg = "Failed to query page of holidays")
    public Page<Holiday> list(HolidayFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<Holiday> sqlQuery = new SQLQueryFetchPage<Holiday>()
                .useRowMapper(new HolidayRowMapper())
                .useQuery(QRY_PAGE_OF_H)
                .useCountQuery(QRY_COUNT_OF_H)
                .useFilter(FILTER_H_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find holiday by ID",
            msgOut = "Querying to find holiday by ID OK",
            errorMsg = "Failed query to find holiday by ID")
    public Optional<Holiday> find(HolidayByIdFinder finder) {

        final SQLQueryFetchOne<Holiday> sqlQuery = new SQLQueryFetchOne<Holiday>()
                .useRowMapper(new HolidayRowMapper())
                .useQuery(QRY_LIST_OF_H)
                .useFilter(FILTER_H_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new holiday",
            msgOut = "New holiday persisted OK",
            errorMsg = "Failed to persist new holiday")
    public Holiday create(HolidayCreate create) {

        final AttributeMap params = create.collectAttributes();

        final HolidayByIdFinder finder = new HolidayByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_H)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for holiday",
            msgOut = "Update for holiday persisted OK",
            errorMsg = "Failed to persist update for holiday")
    public Holiday update(HolidayUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final HolidayByIdFinder finder = new HolidayByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_H)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for holiday",
            msgOut = "Delete for holiday persisted OK",
            errorMsg = "Failed to persist delete for holiday")
    public void delete(HolidayDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_H)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(SQLOrderByType order, String orderBy, SQLQueryFetchMany<Holiday> sqlQuery) {

        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id") ? orderBy : COL_H_ID;
        final SQLOrderByType orderStatement = order != null ? order : ASC;

        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }
}
