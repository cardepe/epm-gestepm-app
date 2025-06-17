package com.epm.gestepm.model.project.dao;

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
import com.epm.gestepm.model.project.dao.entity.Project;
import com.epm.gestepm.model.project.dao.entity.creator.ProjectCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectDelete;
import com.epm.gestepm.model.project.dao.entity.filter.ProjectFilter;
import com.epm.gestepm.model.project.dao.entity.finder.ProjectByIdFinder;
import com.epm.gestepm.model.project.dao.entity.updater.ProjectUpdate;
import com.epm.gestepm.model.project.dao.mappers.ProjectRowMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.project.dao.constants.ProjectQueries.*;
import static com.epm.gestepm.model.project.dao.constants.UserQueries.*;
import static com.epm.gestepm.model.project.dao.mappers.ProjectRowMapper.*;
import static com.epm.gestepm.model.project.dao.mappers.UserRowMapper.*;

@AllArgsConstructor
@Component("projectDao")
@EnableExecutionLog(layerMarker = DAO)
public class ProjectDaoImpl implements ProjectDao {

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of projects",
            msgOut = "Querying list of projects OK",
            errorMsg = "Failed to query list of projects")
    public List<Project> list(ProjectFilter filter) {

        final SQLQueryFetchMany<Project> sqlQuery = new SQLQueryFetchMany<Project>()
                .useRowMapper(new ProjectRowMapper())
                .useQuery(QRY_LIST_OF_PR)
                .useFilter(FILTER_PR_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of projects",
            msgOut = "Querying list of projects OK",
            errorMsg = "Failed to query list of projects")
    public Page<Project> list(ProjectFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<Project> sqlQuery = new SQLQueryFetchPage<Project>()
                .useRowMapper(new ProjectRowMapper())
                .useQuery(QRY_PAGE_OF_PR)
                .useCountQuery(QRY_COUNT_OF_PR)
                .useFilter(FILTER_PR_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find project by ID",
            msgOut = "Querying to find project by ID OK",
            errorMsg = "Failed query to find project by ID")
    public Optional<Project> find(ProjectByIdFinder finder) {

        final SQLQueryFetchOne<Project> sqlQuery = new SQLQueryFetchOne<Project>()
                .useRowMapper(new ProjectRowMapper())
                .useQuery(QRY_LIST_OF_PR)
                .useFilter(FILTER_PR_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new project",
            msgOut = "New project persisted OK",
            errorMsg = "Failed to persist new project")
    public Project create(ProjectCreate create) {

        final AttributeMap params = create.collectAttributes();

        final ProjectByIdFinder finder = new ProjectByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_PR)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for project",
            msgOut = "Update for project persisted OK",
            errorMsg = "Failed to persist update for project")
    public Project update(ProjectUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final ProjectByIdFinder finder = new ProjectByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_PR)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for project",
            msgOut = "Delete for project persisted OK",
            errorMsg = "Failed to persist delete for project")
    public void delete(ProjectDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PR)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<Project> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_PR_ID;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.ASC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("activityCenter.name".equals(orderBy)) {
            return COL_PR_ACTIVITY_CENTER_NAME;
        } else if ("startDate".equals(orderBy)) {
            return COL_PR_START_DATE;
        } else if ("endDate".equals(orderBy)) {
            return COL_PR_END_DATE;
        }
        return List.of(orderBy);
    }
}
