package com.epm.gestepm.model.shares.share.dao;

import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.orderby.SQLOrderByType;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchPage;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.share.dao.entity.Share;
import com.epm.gestepm.model.shares.share.dao.entity.filter.ShareFilter;
import com.epm.gestepm.model.shares.share.dao.mappers.ShareRowMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.shares.share.dao.constants.ShareQueries.*;
import static com.epm.gestepm.model.shares.share.dao.mappers.ShareRowMapper.*;

@AllArgsConstructor
@Component("shareDao")
@EnableExecutionLog(layerMarker = DAO)
public class ShareDaoImpl implements ShareDao {

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of shares",
            msgOut = "Querying list of shares OK",
            errorMsg = "Failed to query list of shares")
    public List<Share> list(ShareFilter filter) {

        final SQLQueryFetchMany<Share> sqlQuery = new SQLQueryFetchMany<Share>()
                .useRowMapper(new ShareRowMapper())
                .useQuery(QRY_LIST_OF_S)
                .useFilter(FILTER_S_BY_PARAMS)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of shares",
            msgOut = "Querying list of shares OK",
            errorMsg = "Failed to query list of shares")
    public Page<Share> list(ShareFilter filter, Long offset, Long limit) {

        final SQLQueryFetchPage<Share> sqlQuery = new SQLQueryFetchPage<Share>()
                .useRowMapper(new ShareRowMapper())
                .useQuery(QRY_PAGE_OF_S)
                .useCountQuery(QRY_COUNT_OF_S)
                .useFilter(FILTER_S_BY_PARAMS)
                .offset(offset)
                .limit(limit)
                .withParams(filter.collectAttributes());

        this.setOrder(filter.getOrder(), filter.getOrderBy(), sqlQuery);

        return this.sqlDatasource.fetch(sqlQuery);
    }

    private void setOrder(final SQLOrderByType order, final String orderBy, final SQLQueryFetchMany<Share> sqlQuery) {
        final String orderByStatement = StringUtils.isNoneBlank(orderBy) && !orderBy.equals("id")
                ? this.getOrderColumn(orderBy)
                : COL_S_ID;
        final SQLOrderByType orderStatement = order != null
                ? order
                : SQLOrderByType.DESC;
        sqlQuery.addOrderBy(orderByStatement, orderStatement);
    }

    private String getOrderColumn(final String orderBy) {
        if ("user.name".equals(orderBy)) {
            return COL_S_U_USERNAME;
        } else if ("project.name".equals(orderBy)) {
            return COL_S_P_NAME;
        } else if ("startDate".equals(orderBy)) {
            return COL_S_START_DATE;
        } else if ("endDate".equals(orderBy)) {
            return COL_S_END_DATE;
        }
        return orderBy;
    }
}
