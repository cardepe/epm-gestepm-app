package com.epm.gestepm.model.shares.noprogrammed.dao;

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
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.ShareFile;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareCreate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter.NoProgrammedShareDelete;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFilter;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareUpdate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.ShareFileUpdate;
import com.epm.gestepm.model.shares.noprogrammed.dao.mappers.NoProgrammedShareRSManyExtractor;
import com.epm.gestepm.model.shares.noprogrammed.dao.mappers.NoProgrammedShareRSOneExtractor;
import com.epm.gestepm.model.shares.noprogrammed.dao.mappers.NoProgrammedShareRowMapper;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.ShareFileDto;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileQueries.QRY_CREATE_NPSF;
import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareQueries.*;

@Component("noProgrammedShareDao")
@EnableExecutionLog(layerMarker = DAO)
public class NoProgrammedShareDaoImpl implements NoProgrammedShareDao {

  private final SQLDatasource sqlDatasource;

  public NoProgrammedShareDaoImpl(SQLDatasource sqlDatasource) {
    this.sqlDatasource = sqlDatasource;
  }

  @Override
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Querying list of no programmed shares",
          msgOut = "Querying list of no programmed shares OK",
          errorMsg = "Failed to query list of no programmed shares")
  public List<NoProgrammedShare> list(NoProgrammedShareFilter filter) {

    final SQLQueryFetchMany<NoProgrammedShare> sqlQuery = new SQLQueryFetchMany<NoProgrammedShare>()
        .useRsExtractor(new NoProgrammedShareRSManyExtractor())
        .useQuery(QRY_LIST_OF_NPS)
        .useFilter(FILTER_NPS_BY_PARAMS)
        .withParams(filter.collectAttributes());

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
        .useRsExtractor(new NoProgrammedShareRSManyExtractor())
        .useQuery(QRY_PAGE_OF_NPS)
        .useCountQuery(QRY_COUNT_OF_NPS)
        .useFilter(FILTER_NPS_BY_PARAMS)
        .offset(offset)
        .limit(limit)
        .withParams(filter.collectAttributes());

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
        .useRsExtractor(new NoProgrammedShareRSOneExtractor())
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

    if (!update.getFiles().isEmpty()) {
      this.insertFiles(update.getFiles(), update.getId());
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

  private void insertFiles(final Set<ShareFileUpdate> files, final Integer shareId) {

    files.forEach(fileUpdate -> {
      fileUpdate.setShareId(shareId);

      final AttributeMap params = fileUpdate.collectAttributes();

      final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
              .useQuery(QRY_CREATE_NPSF)
              .withParams(params);

      this.sqlDatasource.execute(sqlInsert);
    });
  }
}
