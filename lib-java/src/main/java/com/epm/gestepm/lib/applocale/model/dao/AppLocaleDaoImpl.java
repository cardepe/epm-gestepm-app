package com.epm.gestepm.lib.applocale.model.dao;

import com.epm.gestepm.lib.applocale.model.dao.constants.AppLocaleQueries;
import com.epm.gestepm.lib.applocale.model.dao.entity.AppLocale;
import com.epm.gestepm.lib.applocale.model.dao.entity.filter.AppLocaleFilter;
import com.epm.gestepm.lib.applocale.model.dao.entity.finder.AppLocaleByIdFinder;
import com.epm.gestepm.lib.applocale.model.dao.mappers.AppLocaleRowMapper;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchPage;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.types.Page;

import java.util.List;
import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;

@EnableExecutionLog(layerMarker = DAO)
public class AppLocaleDaoImpl implements AppLocaleDao {

    private final SQLDatasource sqlDatasource;

    public AppLocaleDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of app locales",
            msgOut = "Querying list of app locales OK",
            errorMsg = "Failed to query list of app locales")
    public List<AppLocale> list(AppLocaleFilter filter) {

        final SQLQueryFetchMany<AppLocale> sqlQuery = new SQLQueryFetchMany<AppLocale>()
                .useRowMapper(new AppLocaleRowMapper())
                .useQuery(AppLocaleQueries.QRY_LIST_OF_AL)
                .useFilter(AppLocaleQueries.FILTER_AL_BY_PARAMS)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying page of app locales",
            msgOut = "Querying page of app locales OK",
            errorMsg = "Failed to query page of app locales")
    public Page<AppLocale> list(AppLocaleFilter filter, Long offset,
                                Long limit) {

        final SQLQueryFetchPage<AppLocale> sqlQuery = new SQLQueryFetchPage<AppLocale>()
                .useRowMapper(new AppLocaleRowMapper())
                .useQuery(AppLocaleQueries.QRY_PAGE_OF_AL)
                .useCountQuery(AppLocaleQueries.QRY_COUNT_OF_AL)
                .useFilter(AppLocaleQueries.FILTER_AL_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find app locale by ID",
            msgOut = "Querying to find app locale by ID OK",
            errorMsg = "Failed query to find app locale by ID")
    public Optional<AppLocale> find(AppLocaleByIdFinder finder) {

        final SQLQueryFetchOne<AppLocale> sqlQuery = new SQLQueryFetchOne<AppLocale>()
                .useRowMapper(new AppLocaleRowMapper())
                .useQuery(AppLocaleQueries.QRY_LIST_OF_AL)
                .useFilter(AppLocaleQueries.FILTER_AL_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

}
