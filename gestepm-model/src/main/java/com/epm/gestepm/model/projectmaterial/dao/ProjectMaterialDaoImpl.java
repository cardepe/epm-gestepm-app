package com.epm.gestepm.model.projectmaterial.dao;

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
import com.epm.gestepm.model.projectmaterial.dao.entity.ProjectMaterial;
import com.epm.gestepm.model.projectmaterial.dao.entity.creator.ProjectMaterialCreate;
import com.epm.gestepm.model.projectmaterial.dao.entity.deleter.ProjectMaterialDelete;
import com.epm.gestepm.model.projectmaterial.dao.entity.filter.ProjectMaterialFilter;
import com.epm.gestepm.model.projectmaterial.dao.entity.finder.ProjectMaterialByIdFinder;
import com.epm.gestepm.model.projectmaterial.dao.entity.updater.ProjectMaterialUpdate;
import com.epm.gestepm.model.projectmaterial.dao.mappers.ProjectMaterialRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.projectmaterial.dao.constants.ProjectMaterialQueries.*;
import static com.epm.gestepm.model.projectmaterial.dao.mappers.ProjectMaterialRowMapper.*;

@Component("projectMaterialDao")
@EnableExecutionLog(layerMarker = DAO)
public class ProjectMaterialDaoImpl implements ProjectMaterialDao {

    private final SQLDatasource sqlDatasource;

    public ProjectMaterialDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of teleworking signings",
            msgOut = "Querying list of teleworking signings OK",
            errorMsg = "Failed to query list of teleworking signings")
    public List<ProjectMaterial> list(ProjectMaterialFilter filter) {

        final SQLQueryFetchMany<ProjectMaterial> sqlQuery = new SQLQueryFetchMany<ProjectMaterial>()
                .useRowMapper(new ProjectMaterialRowMapper())
                .useQuery(QRY_LIST_OF_PRMAT)
                .useFilter(FILTER_PRMAT_BY_PARAMS)
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
    public Page<ProjectMaterial> list(ProjectMaterialFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<ProjectMaterial> sqlQuery = new SQLQueryFetchPage<ProjectMaterial>()
                .useRowMapper(new ProjectMaterialRowMapper())
                .useQuery(QRY_PAGE_OF_PRMAT)
                .useCountQuery(QRY_COUNT_OF_PRMAT)
                .useFilter(FILTER_PRMAT_BY_PARAMS)
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
    public Optional<ProjectMaterial> find(ProjectMaterialByIdFinder finder) {

        final SQLQueryFetchOne<ProjectMaterial> sqlQuery = new SQLQueryFetchOne<ProjectMaterial>()
                .useRowMapper(new ProjectMaterialRowMapper())
                .useQuery(QRY_LIST_OF_PRMAT)
                .useFilter(FILTER_PRMAT_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new teleworking signing",
            msgOut = "New teleworking signing persisted OK",
            errorMsg = "Failed to persist new teleworking signing")
    public ProjectMaterial create(ProjectMaterialCreate create) {

        final AttributeMap params = create.collectAttributes();

        final ProjectMaterialByIdFinder finder = new ProjectMaterialByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_PRMAT)
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
    public ProjectMaterial update(ProjectMaterialUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final ProjectMaterialByIdFinder finder = new ProjectMaterialByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_PRMAT)
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
    public void delete(ProjectMaterialDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PRMAT)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<ProjectMaterial> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_PRMAT_NAME_ES;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.ASC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("nameEs".equals(orderBy)) {
            return COL_PRMAT_NAME_ES;
        } else if ("nameFr".equals(orderBy)) {
            return COL_PRMAT_NAME_FR;
        }
        return orderBy;
    }
}
