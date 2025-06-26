package com.epm.gestepm.model.project.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.project.dao.entity.creator.ProjectLeaderCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectLeaderDelete;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.model.project.dao.constants.ProjectLeaderQueries.QRY_CREATE_PRL;
import static com.epm.gestepm.model.project.dao.constants.ProjectLeaderQueries.QRY_DELETE_PRL;

@AllArgsConstructor
@Component("projectLeaderDao")
@EnableExecutionLog(layerMarker = DAO)
public class ProjectLeaderDaoImpl implements ProjectLeaderDao {

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new project leader",
            msgOut = "New project leader persisted OK",
            errorMsg = "Failed to persist new project leader")
    public void create(final ProjectLeaderCreate create) {

        final AttributeMap params = create.collectAttributes();

        final SQLQuery sqlInsert = new SQLQuery()
                .useQuery(QRY_CREATE_PRL)
                .withParams(params);

        this.sqlDatasource.execute(sqlInsert);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for project leader",
            msgOut = "Delete for project leader persisted OK",
            errorMsg = "Failed to persist delete for project leader")
    public void delete(final ProjectLeaderDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PRL)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }
}
