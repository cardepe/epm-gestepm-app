package com.epm.gestepm.masterdata.activitycenter.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchPage;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.ActivityCenter;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.creator.ActivityCenterCreate;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.deleter.ActivityCenterDelete;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.filter.ActivityCenterFilter;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.finder.ActivityCenterByIdFinder;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.updater.ActivityCenterUpdate;
import com.epm.gestepm.masterdata.activitycenter.dao.mappers.ActivityCenterRowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.masterdata.activitycenter.dao.constants.ActivityCenterQueries.*;

@Component("activityCenterDao")
@EnableExecutionLog(layerMarker = DAO)
public class ActivityCenterDaoImpl implements ActivityCenterDao {

  private final SQLDatasource sqlDatasource;

  public ActivityCenterDaoImpl(SQLDatasource sqlDatasource) {
    this.sqlDatasource = sqlDatasource;
  }

  @Override
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Querying list of activity centers",
          msgOut = "Querying list of activity centers OK",
          errorMsg = "Failed to query list of activity centers")
  public List<ActivityCenter> list(ActivityCenterFilter filter) {

    final SQLQueryFetchMany<ActivityCenter> sqlQuery = new SQLQueryFetchMany<ActivityCenter>()
        .useRowMapper(new ActivityCenterRowMapper())
        .useQuery(QRY_LIST_OF_AC)
        .useFilter(FILTER_AC_BY_PARAMS)
        .withParams(filter.collectAttributes());

    return this.sqlDatasource.fetch(sqlQuery);
  }

  @Override
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Querying page of activity centers",
          msgOut = "Querying page of activity centers OK",
          errorMsg = "Failed to query page of activity centers")
  public Page<ActivityCenter> list(ActivityCenterFilter filter, Long offset, Long limit) {

    final SQLQueryFetchPage<ActivityCenter> sqlQuery = new SQLQueryFetchPage<ActivityCenter>()
        .useRowMapper(new ActivityCenterRowMapper())
        .useQuery(QRY_PAGE_OF_AC)
        .useCountQuery(QRY_COUNT_OF_AC)
        .useFilter(FILTER_AC_BY_PARAMS)
        .offset(offset)
        .limit(limit)
        .withParams(filter.collectAttributes());

    return this.sqlDatasource.fetch(sqlQuery);
  }

  @Override
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Querying to find activity center by ID",
          msgOut = "Querying to find activity center by ID OK",
          errorMsg = "Failed query to find activity center by ID")
  public Optional<ActivityCenter> find(ActivityCenterByIdFinder finder) {

    final SQLQueryFetchOne<ActivityCenter> sqlQuery = new SQLQueryFetchOne<ActivityCenter>()
        .useRowMapper(new ActivityCenterRowMapper())
        .useQuery(QRY_LIST_OF_AC)
        .useFilter(FILTER_AC_BY_ID)
        .withParams(finder.collectAttributes());

    return this.sqlDatasource.fetch(sqlQuery);
  }

  @Override
  @LogExecution(operation = OP_CREATE,
          debugOut = true,
          msgIn = "Persisting new activity center",
          msgOut = "New activity center persisted OK",
          errorMsg = "Failed to persist new activity center")
  public ActivityCenter create(ActivityCenterCreate create) {

    final AttributeMap params = create.collectAttributes();

    final ActivityCenterByIdFinder finder = new ActivityCenterByIdFinder();

    final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
            .useQuery(QRY_CREATE_AC)
            .withParams(params)
            .onGeneratedKey(f -> finder.setId(f.intValue()));

    this.sqlDatasource.insert(sqlInsert);

    return this.find(finder).orElse(null);
  }

  @Override
  @LogExecution(operation = OP_UPDATE,
          debugOut = true,
          msgIn = "Persisting update for activity center",
          msgOut = "Update for activity center persisted OK",
          errorMsg = "Failed to persist update for activity center")
  public ActivityCenter update(ActivityCenterUpdate update) {

    final Integer id = update.getId();
    final AttributeMap params = update.collectAttributes();

    final ActivityCenterByIdFinder finder = new ActivityCenterByIdFinder();
    finder.setId(id);

    final SQLQuery sqlQuery = new SQLQuery()
            .useQuery(QRY_UPDATE_AC)
            .withParams(params);

    this.sqlDatasource.execute(sqlQuery);

    return this.find(finder).orElse(null);
  }

  @Override
  @LogExecution(operation = OP_DELETE,
          debugOut = true,
          msgIn = "Persisting delete for activity center",
          msgOut = "Delete for activity center persisted OK",
          errorMsg = "Failed to persist delete for activity center")
  public void delete(ActivityCenterDelete delete) {

    final AttributeMap params = delete.collectAttributes();

    final SQLQuery sqlQuery = new SQLQuery()
            .useQuery(QRY_DELETE_AC)
            .withParams(params);

    this.sqlDatasource.execute(sqlQuery);
  }

}
