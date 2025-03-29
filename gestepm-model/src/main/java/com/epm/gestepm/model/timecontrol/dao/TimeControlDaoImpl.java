package com.epm.gestepm.model.timecontrol.dao;

import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchMany;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.timecontrol.dao.entity.TimeControl;
import com.epm.gestepm.model.timecontrol.dao.entity.filter.TimeControlFilter;
import com.epm.gestepm.model.timecontrol.dao.mappers.TimeControlRowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.timecontrol.dao.constants.TimeControlQueries.FILTER_TC_BY_PARAMS;
import static com.epm.gestepm.model.timecontrol.dao.constants.TimeControlQueries.QRY_LIST_OF_TC;

@Component("timeControlDao")
@EnableExecutionLog(layerMarker = DAO)
public class TimeControlDaoImpl implements TimeControlDao {

    private final SQLDatasource sqlDatasource;

    public TimeControlDaoImpl(SQLDatasource sqlDatasource) {
        this.sqlDatasource = sqlDatasource;
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying list of time controls",
            msgOut = "Querying list of time controls OK",
            errorMsg = "Failed to query list of time controls")
    public List<TimeControl> list(TimeControlFilter filter) {

        final SQLQueryFetchMany<TimeControl> sqlQuery = new SQLQueryFetchMany<TimeControl>()
                .useRowMapper(new TimeControlRowMapper())
                .useQuery(QRY_LIST_OF_TC)
                .useFilter(FILTER_TC_BY_PARAMS)
                .withParams(filter.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }
}
