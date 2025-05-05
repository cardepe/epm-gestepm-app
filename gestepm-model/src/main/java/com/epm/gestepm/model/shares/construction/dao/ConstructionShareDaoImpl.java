package com.epm.gestepm.model.shares.construction.dao;

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
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShare;
import com.epm.gestepm.model.shares.construction.dao.entity.filter.ConstructionShareFilter;
import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareCreate;
import com.epm.gestepm.model.shares.construction.dao.entity.deleter.ConstructionShareDelete;
import com.epm.gestepm.model.shares.construction.dao.entity.finder.ConstructionShareByIdFinder;
import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareUpdate;
import com.epm.gestepm.model.shares.construction.dao.mappers.ConstructionShareRowMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareQueries.*;
import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareQueries.QRY_DELETE_CS;
import static com.epm.gestepm.model.shares.construction.dao.mappers.ConstructionShareRowMapper.*;

@AllArgsConstructor
@Component("constructionShareDao")
@EnableExecutionLog(layerMarker = DAO)
public class ConstructionShareDaoImpl implements ConstructionShareDao {

    private final ConstructionShareFileDao constructionShareFileDao;

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of construction shares",
            msgOut = "Querying list of construction shares OK",
            errorMsg = "Failed to query list of construction shares")
    public List<ConstructionShare> list(ConstructionShareFilter filter) {

        final SQLQueryFetchMany<ConstructionShare> sqlQuery = new SQLQueryFetchMany<ConstructionShare>()
                .useRowMapper(new ConstructionShareRowMapper())
                .useQuery(QRY_LIST_OF_CS)
                .useFilter(FILTER_CS_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of construction shares",
            msgOut = "Querying list of construction shares OK",
            errorMsg = "Failed to query list of construction shares")
    public Page<ConstructionShare> list(ConstructionShareFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<ConstructionShare> sqlQuery = new SQLQueryFetchPage<ConstructionShare>()
                .useRowMapper(new ConstructionShareRowMapper())
                .useQuery(QRY_PAGE_OF_CS)
                .useCountQuery(QRY_COUNT_OF_CS)
                .useFilter(FILTER_CS_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find construction share by ID",
            msgOut = "Querying to find construction share by ID OK",
            errorMsg = "Failed query to find construction share by ID")
    public Optional<ConstructionShare> find(ConstructionShareByIdFinder finder) {

        final SQLQueryFetchOne<ConstructionShare> sqlQuery = new SQLQueryFetchOne<ConstructionShare>()
                .useRowMapper(new ConstructionShareRowMapper())
                .useQuery(QRY_LIST_OF_CS)
                .useFilter(FILTER_CS_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new construction share",
            msgOut = "New construction share persisted OK",
            errorMsg = "Failed to persist new construction share")
    public ConstructionShare create(ConstructionShareCreate create) {

        final AttributeMap params = create.collectAttributes();

        final ConstructionShareByIdFinder finder = new ConstructionShareByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_CS)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for construction share",
            msgOut = "Update for construction share persisted OK",
            errorMsg = "Failed to persist update for construction share")
    public ConstructionShare update(ConstructionShareUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final ConstructionShareByIdFinder finder = new ConstructionShareByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_CS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        if (CollectionUtils.isNotEmpty(update.getFiles())) {
            update.getFiles().stream()
                    .peek(file -> file.setShareId(id))
                    .forEach(constructionShareFileDao::create);
        }

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for construction share",
            msgOut = "Delete for construction share persisted OK",
            errorMsg = "Failed to persist delete for construction share")
    public void delete(ConstructionShareDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_CS)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<ConstructionShare> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_CS_ID;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.DESC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("user.name".equals(orderBy)) {
            return COL_CS_U_USERNAME;
        } else if ("project.name".equals(orderBy)) {
            return COL_CS_P_NAME;
        } else if ("startDate".equals(orderBy)) {
            return COL_CS_START_DATE;
        } else if ("endDate".equals(orderBy)) {
            return COL_CS_END_DATE;
        }
        return orderBy;
    }
}
