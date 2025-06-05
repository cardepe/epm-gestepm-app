package com.epm.gestepm.model.user.dao;

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
import com.epm.gestepm.model.user.dao.entity.User;
import com.epm.gestepm.model.user.dao.entity.creator.UserCreate;
import com.epm.gestepm.model.user.dao.entity.deleter.UserDelete;
import com.epm.gestepm.model.user.dao.entity.filter.UserFilter;
import com.epm.gestepm.model.user.dao.entity.finder.UserByIdFinder;
import com.epm.gestepm.model.user.dao.entity.updater.UserUpdate;
import com.epm.gestepm.model.user.dao.mappers.UserRowMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.inspection.dao.mappers.InspectionRowMapper.COL_I_START_DATE;
import static com.epm.gestepm.model.user.dao.constants.UserQueries.*;
import static com.epm.gestepm.model.user.dao.mappers.UserRowMapper.*;

@AllArgsConstructor
@Component("userDao")
@EnableExecutionLog(layerMarker = DAO)
public class UserDaoImpl implements UserDao {

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of users",
            msgOut = "Querying list of users OK",
            errorMsg = "Failed to query list of users")
    public List<User> list(UserFilter filter) {

        final SQLQueryFetchMany<User> sqlQuery = new SQLQueryFetchMany<User>()
                .useRowMapper(new UserRowMapper())
                .useQuery(QRY_LIST_OF_U)
                .useFilter(FILTER_U_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of users",
            msgOut = "Querying list of users OK",
            errorMsg = "Failed to query list of users")
    public Page<User> list(UserFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<User> sqlQuery = new SQLQueryFetchPage<User>()
                .useRowMapper(new UserRowMapper())
                .useQuery(QRY_PAGE_OF_U)
                .useCountQuery(QRY_COUNT_OF_U)
                .useFilter(FILTER_U_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find user by ID",
            msgOut = "Querying to find user by ID OK",
            errorMsg = "Failed query to find user by ID")
    public Optional<User> find(UserByIdFinder finder) {

        final SQLQueryFetchOne<User> sqlQuery = new SQLQueryFetchOne<User>()
                .useRowMapper(new UserRowMapper())
                .useQuery(QRY_LIST_OF_U)
                .useFilter(FILTER_U_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new user",
            msgOut = "New user persisted OK",
            errorMsg = "Failed to persist new user")
    public User create(UserCreate create) {

        final AttributeMap params = create.collectAttributes();

        final UserByIdFinder finder = new UserByIdFinder();

        final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
                .useQuery(QRY_CREATE_U)
                .withParams(params)
                .onGeneratedKey(f -> finder.setId(f.intValue()));

        this.sqlDatasource.insert(sqlInsert);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Persisting update for user",
            msgOut = "Update for user persisted OK",
            errorMsg = "Failed to persist update for user")
    public User update(UserUpdate update) {

        final Integer id = update.getId();
        final AttributeMap params = update.collectAttributes();

        final UserByIdFinder finder = new UserByIdFinder();
        finder.setId(id);

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_UPDATE_U)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        return this.find(finder).orElse(null);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for user",
            msgOut = "Delete for user persisted OK",
            errorMsg = "Failed to persist delete for user")
    public void delete(UserDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_U)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<User> sqlQuery) {
        final List<String> orderByStatements = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : List.of(COL_U_ID);
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.ASC;
        sqlQuery.addOrderBy(orderByStatements, orderStatement);
    }

    private List<String> getOrderColumn(final String orderBy) {
        if ("activityCenter.name".equals(orderBy)) {
            return List.of(COL_U_ACTIVITY_CENTER_NAME);
        } else if ("fullName".equals(orderBy)) {
            return List.of(COL_U_NAME, COL_U_SURNAMES);
        } else if ("role.name".equals(orderBy)) {
            return List.of(COL_U_ROLE_NAME);
        } else if ("level.name".equals(orderBy)) {
            return List.of(COL_U_LEVEL_NAME);
        }
        return List.of(orderBy);
    }
}
