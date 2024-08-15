package com.epm.gestepm.model.constructionshare.dao;

import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchPage;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.constructionshare.dao.entity.ConstructionShare;
import com.epm.gestepm.model.constructionshare.dao.entity.filter.ConstructionShareFilter;
import com.epm.gestepm.model.constructionshare.dao.mappers.ConstructionShareRSManyExtractor;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import static com.epm.gestepm.model.constructionshare.dao.constants.ConstructionShareQueries.*;

@Component("constructionDao")
@EnableExecutionLog(layerMarker = DAO)
public class ConstructionShareDaoImpl implements ConstructionShareDao {

    private final SQLDatasource sqlDatasource;

    public ConstructionShareDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of construction shares",
            msgOut = "Querying list of construction shares OK",
            errorMsg = "Failed to query list of construction shares")
    public Page<ConstructionShare> list(ConstructionShareFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<ConstructionShare> sqlQuery = new SQLQueryFetchPage<ConstructionShare>()
                .useRsExtractor(new ConstructionShareRSManyExtractor())
                .useQuery(QRY_PAGE_OF_CS)
                .useCountQuery(QRY_COUNT_OF_CS)
                .useFilter(FILTER_CS_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }
}
