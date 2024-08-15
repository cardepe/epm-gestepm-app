package com.epm.gestepm.masterdata.country.dao;

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
import com.epm.gestepm.masterdata.country.dao.entity.Country;
import com.epm.gestepm.masterdata.country.dao.entity.creator.CountryCreate;
import com.epm.gestepm.masterdata.country.dao.entity.deleter.CountryDelete;
import com.epm.gestepm.masterdata.country.dao.entity.filter.CountryFilter;
import com.epm.gestepm.masterdata.country.dao.entity.finder.CountryByIdFinder;
import com.epm.gestepm.masterdata.country.dao.entity.updater.CountryUpdate;
import com.epm.gestepm.masterdata.country.dao.mappers.CountryRowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.masterdata.country.dao.constants.CountryQueries.*;

@Component("countryDao")
@EnableExecutionLog(layerMarker = DAO)
public class CountryDaoImpl implements CountryDao {

  private final SQLDatasource sqlDatasource;

  public CountryDaoImpl(SQLDatasource sqlDatasource) {
    this.sqlDatasource = sqlDatasource;
  }

  @Override
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Querying list of countries",
          msgOut = "Querying list of countries OK",
          errorMsg = "Failed to query list of countries")
  public List<Country> list(CountryFilter filter) {

    final SQLQueryFetchMany<Country> sqlQuery = new SQLQueryFetchMany<Country>()
        .useRowMapper(new CountryRowMapper())
        .useQuery(QRY_LIST_OF_C)
        .useFilter(FILTER_C_BY_PARAMS)
        .withParams(filter.collectAttributes());

    return this.sqlDatasource.fetch(sqlQuery);
  }

  @Override
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Querying page of countries",
          msgOut = "Querying page of countries OK",
          errorMsg = "Failed to query page of countries")
  public Page<Country> list(CountryFilter filter, Long offset, Long limit) {

    final SQLQueryFetchPage<Country> sqlQuery = new SQLQueryFetchPage<Country>()
        .useRowMapper(new CountryRowMapper())
        .useQuery(QRY_PAGE_OF_C)
        .useCountQuery(QRY_COUNT_OF_C)
        .useFilter(FILTER_C_BY_PARAMS)
        .offset(offset)
        .limit(limit)
        .withParams(filter.collectAttributes());

    return this.sqlDatasource.fetch(sqlQuery);
  }

  @Override
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Querying to find country by ID",
          msgOut = "Querying to find country by ID OK",
          errorMsg = "Failed query to find country by ID")
  public Optional<Country> find(CountryByIdFinder finder) {

    final SQLQueryFetchOne<Country> sqlQuery = new SQLQueryFetchOne<Country>()
        .useRowMapper(new CountryRowMapper())
        .useQuery(QRY_LIST_OF_C)
        .useFilter(FILTER_C_BY_ID)
        .withParams(finder.collectAttributes());

    return this.sqlDatasource.fetch(sqlQuery);
  }

  @Override
  @LogExecution(operation = OP_CREATE,
          debugOut = true,
          msgIn = "Persisting new country",
          msgOut = "New country persisted OK",
          errorMsg = "Failed to persist new country")
  public Country create(CountryCreate create) {

    final AttributeMap params = create.collectAttributes();

    final CountryByIdFinder finder = new CountryByIdFinder();

    final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
            .useQuery(QRY_CREATE_C)
            .withParams(params)
            .onGeneratedKey(f -> finder.setId(f.intValue()));

    this.sqlDatasource.insert(sqlInsert);

    return this.find(finder).orElse(null);
  }

  @Override
  @LogExecution(operation = OP_UPDATE,
          debugOut = true,
          msgIn = "Persisting update for country",
          msgOut = "Update for country persisted OK",
          errorMsg = "Failed to persist update for country")
  public Country update(CountryUpdate update) {

    final Integer id = update.getId();
    final AttributeMap params = update.collectAttributes();

    final CountryByIdFinder finder = new CountryByIdFinder();
    finder.setId(id);

    final SQLQuery sqlQuery = new SQLQuery()
            .useQuery(QRY_UPDATE_C)
            .withParams(params);

    this.sqlDatasource.execute(sqlQuery);

    return this.find(finder).orElse(null);
  }

  @Override
  @LogExecution(operation = OP_DELETE,
          debugOut = true,
          msgIn = "Persisting delete for country",
          msgOut = "Delete for country persisted OK",
          errorMsg = "Failed to persist delete for country")
  public void delete(CountryDelete delete) {

    final AttributeMap params = delete.collectAttributes();

    final SQLQuery sqlQuery = new SQLQuery()
            .useQuery(QRY_DELETE_C)
            .withParams(params);

    this.sqlDatasource.execute(sqlQuery);
  }

}
