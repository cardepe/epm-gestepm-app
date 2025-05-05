package com.epm.gestepm.model.personalexpense.dao;

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
import com.epm.gestepm.model.personalexpense.dao.entity.PersonalExpense;
import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseCreate;
import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseFileCreate;
import com.epm.gestepm.model.personalexpense.dao.entity.deleter.PersonalExpenseDelete;
import com.epm.gestepm.model.personalexpense.dao.entity.filter.PersonalExpenseFilter;
import com.epm.gestepm.model.personalexpense.dao.entity.finder.PersonalExpenseByIdFinder;
import com.epm.gestepm.model.personalexpense.dao.entity.updater.PersonalExpenseUpdate;
import com.epm.gestepm.model.personalexpense.dao.mappers.PersonalExpenseRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.inspection.dao.mappers.InspectionRowMapper.COL_I_START_DATE;
import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseQueries.*;
import static com.epm.gestepm.model.personalexpense.dao.mappers.PersonalExpenseRowMapper.COL_PE_ID;

@Component("personalExpenseDao")
@EnableExecutionLog(layerMarker = DAO)
public class PersonalExpenseDaoImpl implements PersonalExpenseDao {

    private final PersonalExpenseFileDao personalExpenseFileDao;

    private final SQLDatasource sqlDatasource;

    public PersonalExpenseDaoImpl(PersonalExpenseFileDao personalExpenseFileDao, SQLDatasource sqlDatasource) {
        this.personalExpenseFileDao = personalExpenseFileDao;
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of personal expenses",
            msgOut = "Querying list of personal expenses OK",
            errorMsg = "Failed to query list of personal expenses")
    public List<PersonalExpense> list(PersonalExpenseFilter filter) {

        final SQLQueryFetchMany<PersonalExpense> sqlQuery = new SQLQueryFetchMany<PersonalExpense>()
                .useRowMapper(new PersonalExpenseRowMapper())
                .useQuery(QRY_LIST_OF_PE)
                .useFilter(FILTER_PE_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying page of personal expenses",
            msgOut = "Querying page of personal expenses OK",
            errorMsg = "Failed to query page of personal expenses")
    public Page<PersonalExpense> list(PersonalExpenseFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<PersonalExpense> sqlQuery = new SQLQueryFetchPage<PersonalExpense>()
                .useRowMapper(new PersonalExpenseRowMapper())
                .useQuery(QRY_PAGE_OF_PE)
                .useCountQuery(QRY_COUNT_OF_PE)
                .useFilter(FILTER_PE_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find personal expense by ID",
            msgOut = "Querying to find personal expense by ID OK",
            errorMsg = "Failed query to find personal expense by ID")
    public Optional<PersonalExpense> find(PersonalExpenseByIdFinder finder) {

        final SQLQueryFetchOne<PersonalExpense> sqlQuery = new SQLQueryFetchOne<PersonalExpense>()
                .useRowMapper(new PersonalExpenseRowMapper())
                .useQuery(QRY_LIST_OF_PE)
                .useFilter(FILTER_PE_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new personal expense",
            msgOut = "New personal expense persisted OK",
            errorMsg = "Failed to persist new personal expense")
    public PersonalExpense create(PersonalExpenseCreate create) {

        final AttributeMap params = create.collectAttributes();

        final PersonalExpenseByIdFinder finder = new PersonalExpenseByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_PE)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        if (create.getFiles() != null && !create.getFiles().isEmpty()) {
            this.insertFiles(create.getFiles(), finder.getId());
        }

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for personal expense",
            msgOut = "Update for personal expense persisted OK",
            errorMsg = "Failed to persist update for personal expense")
    public PersonalExpense update(PersonalExpenseUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final PersonalExpenseByIdFinder finder = new PersonalExpenseByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_PE)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        if (update.getFiles() != null && !update.getFiles().isEmpty()) {
            this.insertFiles(update.getFiles(), id);
        }

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for personal expense",
            msgOut = "Delete for personal expense persisted OK",
            errorMsg = "Failed to persist delete for personal expense")
    public void delete(PersonalExpenseDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PE)
                .useFilter(FILTER_PE_BY_PARAMS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(SQLOrderByType order, String orderBy, SQLQueryFetchMany<PersonalExpense> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_PE_ID;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.ASC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("startDate".equals(orderBy)) {
            return COL_I_START_DATE;
        }
        return orderBy;
    }

    private void insertFiles(final List<PersonalExpenseFileCreate> files, final Integer personalExpenseId) {
        files.forEach(fileCreate -> {
            fileCreate.setPersonalExpenseId(personalExpenseId);
            this.personalExpenseFileDao.create(fileCreate);
        });
    }
}
