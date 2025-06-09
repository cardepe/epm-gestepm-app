package com.epm.gestepm.model.personalexpensesheet.dao;

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
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheet;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.creator.PersonalExpenseSheetCreate;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.deleter.PersonalExpenseSheetDelete;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.filter.PersonalExpenseSheetFilter;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.finder.PersonalExpenseSheetByIdFinder;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.updater.PersonalExpenseSheetUpdate;
import com.epm.gestepm.model.personalexpensesheet.dao.mappers.PersonalExpenseSheetRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetQueries.*;
import static com.epm.gestepm.model.personalexpensesheet.dao.mappers.PersonalExpenseSheetRowMapper.*;

@Component("personalExpenseSheetDao")
@EnableExecutionLog(layerMarker = DAO)
public class PersonalExpenseSheetDaoImpl implements PersonalExpenseSheetDao {

    private final SQLDatasource sqlDatasource;

    public PersonalExpenseSheetDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of personal expense sheets",
            msgOut = "Querying list of personal expense sheets OK",
            errorMsg = "Failed to query list of personal expense sheets")
    public List<PersonalExpenseSheet> list(PersonalExpenseSheetFilter filter) {

        final SQLQueryFetchMany<PersonalExpenseSheet> sqlQuery = new SQLQueryFetchMany<PersonalExpenseSheet>()
                .useRowMapper(new PersonalExpenseSheetRowMapper())
                .useQuery(QRY_LIST_OF_PES)
                .useFilter(FILTER_PES_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying page of personal expense sheets",
            msgOut = "Querying page of personal expense sheets OK",
            errorMsg = "Failed to query page of personal expense sheets")
    public Page<PersonalExpenseSheet> list(PersonalExpenseSheetFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<PersonalExpenseSheet> sqlQuery = new SQLQueryFetchPage<PersonalExpenseSheet>()
                .useRowMapper(new PersonalExpenseSheetRowMapper())
                .useQuery(QRY_PAGE_OF_PES)
                .useCountQuery(QRY_COUNT_OF_PES)
                .useFilter(FILTER_PES_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find personal expense sheet by ID",
            msgOut = "Querying to find personal expense sheet by ID OK",
            errorMsg = "Failed query to find personal expense sheet by ID")
    public Optional<PersonalExpenseSheet> find(PersonalExpenseSheetByIdFinder finder) {

        final SQLQueryFetchOne<PersonalExpenseSheet> sqlQuery = new SQLQueryFetchOne<PersonalExpenseSheet>()
                .useRowMapper(new PersonalExpenseSheetRowMapper())
                .useQuery(QRY_LIST_OF_PES)
                .useFilter(FILTER_PES_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new personal expense sheet",
            msgOut = "New personal expense sheet persisted OK",
            errorMsg = "Failed to persist new personal expense sheet")
    public PersonalExpenseSheet create(PersonalExpenseSheetCreate create) {

        final AttributeMap params = create.collectAttributes();

        final PersonalExpenseSheetByIdFinder finder = new PersonalExpenseSheetByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_PES)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for personal expense sheet",
            msgOut = "Update for personal expense sheet persisted OK",
            errorMsg = "Failed to persist update for personal expense sheet")
    public PersonalExpenseSheet update(PersonalExpenseSheetUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final PersonalExpenseSheetByIdFinder finder = new PersonalExpenseSheetByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_PES)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for personal expense sheet",
            msgOut = "Delete for personal expense sheet persisted OK",
            errorMsg = "Failed to persist delete for personal expense sheet")
    public void delete(PersonalExpenseSheetDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PES)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<PersonalExpenseSheet> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("createdAt")
                ? this.getOrderColumn(orderBy)
                : COL_PES_CREATED_AT;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.DESC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("id".equals(orderBy)) {
            return COL_PES_ID;
        } else if ("project.name".equals(orderBy)) {
            return COL_PES_PROJECT_ID;
        }
        return orderBy;
    }
}
