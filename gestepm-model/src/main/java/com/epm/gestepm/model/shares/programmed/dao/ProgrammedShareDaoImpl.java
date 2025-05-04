package com.epm.gestepm.model.shares.programmed.dao;

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
import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShare;
import com.epm.gestepm.model.shares.programmed.dao.entity.creator.ProgrammedShareCreate;
import com.epm.gestepm.model.shares.programmed.dao.entity.deleter.ProgrammedShareDelete;
import com.epm.gestepm.model.shares.programmed.dao.entity.filter.ProgrammedShareFilter;
import com.epm.gestepm.model.shares.programmed.dao.entity.finder.ProgrammedShareByIdFinder;
import com.epm.gestepm.model.shares.programmed.dao.entity.updater.ProgrammedShareUpdate;
import com.epm.gestepm.model.shares.programmed.dao.mappers.ProgrammedShareRowMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareQueries.*;
import static com.epm.gestepm.model.shares.programmed.dao.mappers.ProgrammedShareRowMapper.*;

@AllArgsConstructor
@Component("programmedDao")
@EnableExecutionLog(layerMarker = DAO)
public class ProgrammedShareDaoImpl implements ProgrammedShareDao {

    private final ProgrammedShareFileDao programmedShareFileDao;

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of programmed shares",
            msgOut = "Querying list of programmed shares OK",
            errorMsg = "Failed to query list of programmed shares")
    public List<ProgrammedShare> list(ProgrammedShareFilter filter) {

        final SQLQueryFetchMany<ProgrammedShare> sqlQuery = new SQLQueryFetchMany<ProgrammedShare>()
                .useRowMapper(new ProgrammedShareRowMapper())
                .useQuery(QRY_LIST_OF_PS)
                .useFilter(FILTER_PS_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of programmed shares",
            msgOut = "Querying list of programmed shares OK",
            errorMsg = "Failed to query list of programmed shares")
    public Page<ProgrammedShare> list(ProgrammedShareFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<ProgrammedShare> sqlQuery = new SQLQueryFetchPage<ProgrammedShare>()
                .useRowMapper(new ProgrammedShareRowMapper())
                .useQuery(QRY_PAGE_OF_PS)
                .useCountQuery(QRY_COUNT_OF_PS)
                .useFilter(FILTER_PS_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find programmed share by ID",
            msgOut = "Querying to find programmed share by ID OK",
            errorMsg = "Failed query to find programmed share by ID")
    public Optional<ProgrammedShare> find(ProgrammedShareByIdFinder finder) {

        final SQLQueryFetchOne<ProgrammedShare> sqlQuery = new SQLQueryFetchOne<ProgrammedShare>()
                .useRowMapper(new ProgrammedShareRowMapper())
                .useQuery(QRY_LIST_OF_PS)
                .useFilter(FILTER_PS_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new programmed share",
            msgOut = "New programmed share persisted OK",
            errorMsg = "Failed to persist new programmed share")
    public ProgrammedShare create(ProgrammedShareCreate create) {

        final AttributeMap params = create.collectAttributes();

        final ProgrammedShareByIdFinder finder = new ProgrammedShareByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_PS)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for programmed share",
            msgOut = "Update for programmed share persisted OK",
            errorMsg = "Failed to persist update for programmed share")
    public ProgrammedShare update(ProgrammedShareUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final ProgrammedShareByIdFinder finder = new ProgrammedShareByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_PS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        if (CollectionUtils.isNotEmpty(update.getFiles())) {
            update.getFiles().stream()
                    .peek(file -> file.setShareId(id))
                    .forEach(programmedShareFileDao::create);
        }

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for programmed share",
            msgOut = "Delete for programmed share persisted OK",
            errorMsg = "Failed to persist delete for programmed share")
    public void delete(ProgrammedShareDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<ProgrammedShare> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_PS_ID;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.DESC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("user.name".equals(orderBy)) {
            return COL_PS_U_USERNAME;
        } else if ("project.name".equals(orderBy)) {
            return COL_PS_P_NAME;
        } else if ("startDate".equals(orderBy)) {
            return COL_PS_START_DATE;
        } else if ("endDate".equals(orderBy)) {
            return COL_PS_END_DATE;
        }
        return orderBy;
    }
}
