package com.epm.gestepm.model.inspection.dao;

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
import com.epm.gestepm.model.inspection.dao.entity.Inspection;
import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionCreate;
import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionFileCreate;
import com.epm.gestepm.model.inspection.dao.entity.creator.MaterialCreate;
import com.epm.gestepm.model.inspection.dao.entity.deleter.InspectionDelete;
import com.epm.gestepm.model.inspection.dao.entity.deleter.MaterialDelete;
import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFilter;
import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionByIdFinder;
import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionUpdate;
import com.epm.gestepm.model.inspection.dao.mappers.InspectionRSManyExtractor;
import com.epm.gestepm.model.inspection.dao.mappers.InspectionRSOneExtractor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.inspection.dao.constants.InspectionQueries.*;
import static com.epm.gestepm.model.inspection.dao.constants.MaterialQueries.QRY_CREATE_M;
import static com.epm.gestepm.model.inspection.dao.constants.MaterialQueries.QRY_DELETE_M;
import static com.epm.gestepm.model.inspection.dao.mappers.InspectionRowMapper.COL_I_ID;

@Component("inspectionDao")
@EnableExecutionLog(layerMarker = DAO)
public class InspectionDaoImpl implements InspectionDao {

  private final InspectionFileDao inspectionFileDao;

  private final SQLDatasource sqlDatasource;

  public InspectionDaoImpl(InspectionFileDao inspectionFileDao, SQLDatasource sqlDatasource) {
      this.inspectionFileDao = inspectionFileDao;
      this.sqlDatasource = sqlDatasource;
  }

  @Override
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Querying list of inspections",
          msgOut = "Querying list of inspections OK",
          errorMsg = "Failed to query list of inspections")
  public List<Inspection> list(InspectionFilter filter) {

    final SQLQueryFetchMany<Inspection> sqlQuery = new SQLQueryFetchMany<Inspection>()
        .useRsExtractor(new InspectionRSManyExtractor())
        .useQuery(QRY_LIST_OF_I)
        .useFilter(FILTER_I_BY_PARAMS)
        .withParams(filter.collectAttributes());

    this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

    return this.sqlDatasource.fetch(sqlQuery);
  }

  @Override
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Querying page of inspections",
          msgOut = "Querying page of inspections OK",
          errorMsg = "Failed to query page of inspections")
  public Page<Inspection> list(InspectionFilter filter, Long offset, Long limit) {

    final SQLQueryFetchPage<Inspection> sqlQuery = new SQLQueryFetchPage<Inspection>()
        .useRsExtractor(new InspectionRSManyExtractor())
        .useQuery(QRY_PAGE_OF_I)
        .useCountQuery(QRY_COUNT_OF_I)
        .useFilter(FILTER_I_BY_PARAMS)
        .offset(offset)
        .limit(limit)
        .withParams(filter.collectAttributes());

    this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

    return this.sqlDatasource.fetch(sqlQuery);
  }

  @Override
  @LogExecution(operation = OP_READ,
          debugOut = true,
          msgIn = "Querying to find inspection by ID",
          msgOut = "Querying to find inspection by ID OK",
          errorMsg = "Failed query to find inspection by ID")
  public Optional<Inspection> find(InspectionByIdFinder finder) {

    final SQLQueryFetchOne<Inspection> sqlQuery = new SQLQueryFetchOne<Inspection>()
        .useRsExtractor(new InspectionRSOneExtractor())
        .useQuery(QRY_LIST_OF_I)
        .useFilter(FILTER_I_BY_ID)
        .withParams(finder.collectAttributes());

    return this.sqlDatasource.fetch(sqlQuery);
  }

  @Override
  @LogExecution(operation = OP_CREATE,
          debugOut = true,
          msgIn = "Persisting new inspection",
          msgOut = "New inspection persisted OK",
          errorMsg = "Failed to persist new inspection")
  public Inspection create(InspectionCreate create) {

    final AttributeMap params = create.collectAttributes();

    final InspectionByIdFinder finder = new InspectionByIdFinder();

    final SQLInsert<BigInteger> sqlInsert = new SQLInsert<BigInteger>()
            .useQuery(QRY_CREATE_I)
            .withParams(params)
            .onGeneratedKey(f -> finder.setId(f.intValue()));

    this.sqlDatasource.insert(sqlInsert);

    return this.find(finder).orElse(null);
  }

  @Override
  @LogExecution(operation = OP_UPDATE,
          debugOut = true,
          msgIn = "Persisting update for inspection",
          msgOut = "Update for inspection persisted OK",
          errorMsg = "Failed to persist update for inspection")
  public Inspection update(InspectionUpdate update) {

    final Integer id = update.getId();
    final AttributeMap params = update.collectAttributes();

    final InspectionByIdFinder finder = new InspectionByIdFinder();
    finder.setId(id);

    final SQLQuery sqlQuery = new SQLQuery()
            .useQuery(QRY_UPDATE_I)
            .withParams(params);

    this.sqlDatasource.execute(sqlQuery);

    if (update.getMaterials() != null && !update.getMaterials().isEmpty()) {
      this.deleteMaterials(update.getId());
      this.createMaterials(update.getMaterials(), update.getId());
    }

    if (update.getFiles() != null && !update.getFiles().isEmpty()) {
      this.insertFiles(update.getFiles(), update.getId());
    }

    return this.find(finder).orElse(null);
  }

  @Override
  @LogExecution(operation = OP_DELETE,
          debugOut = true,
          msgIn = "Persisting delete for inspection",
          msgOut = "Delete for inspection persisted OK",
          errorMsg = "Failed to persist delete for inspection")
  public void delete(InspectionDelete delete) {

    final AttributeMap params = delete.collectAttributes();

    final SQLQuery sqlQuery = new SQLQuery()
            .useQuery(QRY_DELETE_I)
            .withParams(params);

    this.sqlDatasource.execute(sqlQuery);
  }

  private void createMaterials(final List<MaterialCreate> materials, final Integer inspectionId) {
    materials.forEach(materialCreate -> {
      materialCreate.setInspectionId(inspectionId);

      final AttributeMap params = materialCreate.collectAttributes();

      final SQLQuery sqlQuery = new SQLQuery()
              .useQuery(QRY_CREATE_M)
              .withParams(params);

      this.sqlDatasource.execute(sqlQuery);
    });
  }

  private void deleteMaterials(final Integer inspectionId) {
    final MaterialDelete delete = new MaterialDelete();
    delete.setInspectionId(inspectionId);

    final AttributeMap params = delete.collectAttributes();

    final SQLQuery sqlQuery = new SQLQuery()
            .useQuery(QRY_DELETE_M)
            .withParams(params);

    this.sqlDatasource.execute(sqlQuery);
  }

  private void insertFiles(final Set<InspectionFileCreate> files, final Integer inspectionId) {
    files.forEach(fileCreate -> {
      fileCreate.setInspectionId(inspectionId);
      this.inspectionFileDao.create(fileCreate);
    });
  }

  private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<Inspection> sqlQuery) {
    final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id") ? orderBy : COL_I_ID;
    final SQLOrderByType orderStatement = order != null ? order : SQLOrderByType.ASC;
    sqlQuery.addOrderBy(orderByStatement, orderStatement);
  }
}
