package com.epm.gestepm.model.shares.noprogrammed.dao;

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
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareCreate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter.NoProgrammedShareDelete;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFilter;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareUpdate;
import com.epm.gestepm.model.shares.noprogrammed.dao.mappers.NoProgrammedShareRowMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareQueries.*;
import static com.epm.gestepm.model.shares.noprogrammed.dao.mappers.NoProgrammedShareRowMapper.*;
import static com.epm.gestepm.model.shares.programmed.dao.mappers.ProgrammedShareRowMapper.*;

@AllArgsConstructor
@Component("noProgrammedShareDao")
@EnableExecutionLog(layerMarker = DAO)
public class NoProgrammedShareDaoImpl implements NoProgrammedShareDao {

    private final NoProgrammedShareFileDao noProgrammedShareFileDao;

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of no programmed shares",
            msgOut = "Querying list of no programmed shares OK",
            errorMsg = "Failed to query list of no programmed shares")
    public List<NoProgrammedShare> list(NoProgrammedShareFilter filter) {

        final SQLQueryFetchMany<NoProgrammedShare> sqlQuery = new SQLQueryFetchMany<NoProgrammedShare>()
                .useRowMapper(new NoProgrammedShareRowMapper())
                .useQuery(QRY_LIST_OF_NPS)
                .useFilter(FILTER_NPS_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying page of no programmed shares",
            msgOut = "Querying page of no programmed shares OK",
            errorMsg = "Failed to query page of no programmed shares")
    public Page<NoProgrammedShare> list(NoProgrammedShareFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<NoProgrammedShare> sqlQuery = new SQLQueryFetchPage<NoProgrammedShare>()
                .useRowMapper(new NoProgrammedShareRowMapper())
                .useQuery(QRY_PAGE_OF_NPS)
                .useCountQuery(QRY_COUNT_OF_NPS)
                .useFilter(FILTER_NPS_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find no programmed share by ID",
            msgOut = "Querying to find no programmed share by ID OK",
            errorMsg = "Failed query to find no programmed share by ID")
    public Optional<NoProgrammedShare> find(NoProgrammedShareByIdFinder finder) {

        final SQLQueryFetchOne<NoProgrammedShare> sqlQuery = new SQLQueryFetchOne<NoProgrammedShare>()
                .useRowMapper(new NoProgrammedShareRowMapper())
                .useQuery(QRY_LIST_OF_NPS)
                .useFilter(FILTER_NPS_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new no programmed share",
            msgOut = "New no programmed share persisted OK",
            errorMsg = "Failed to persist new no programmed share")
    public NoProgrammedShare create(NoProgrammedShareCreate create) {

        final AttributeMap params = create.collectAttributes();

        final NoProgrammedShareByIdFinder finder = new NoProgrammedShareByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_NPS)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for no programmed share",
            msgOut = "Update for no programmed share persisted OK",
            errorMsg = "Failed to persist update for no programmed share")
    public NoProgrammedShare update(NoProgrammedShareUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final NoProgrammedShareByIdFinder finder = new NoProgrammedShareByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_NPS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        if (CollectionUtils.isNotEmpty(update.getFiles())) {
            update.getFiles().stream()
                    .peek(file -> file.setShareId(id))
                    .forEach(noProgrammedShareFileDao::create);
        }

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for no programmed share",
            msgOut = "Delete for no programmed share persisted OK",
            errorMsg = "Failed to persist delete for no programmed share")
    public void delete(NoProgrammedShareDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_NPS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<NoProgrammedShare> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_NPS_ID;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.DESC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("user.name".equals(orderBy)) {
            return COL_NPS_USER_ID;
        } else if ("project.name".equals(orderBy)) {
            return COL_NPS_PROJECT_NAME;
        } else if ("startDate".equals(orderBy)) {
            return COL_NPS_START_DATE;
        } else if ("endDate".equals(orderBy)) {
            return COL_NPS_END_DATE;
        }
        return orderBy;
    }
}
