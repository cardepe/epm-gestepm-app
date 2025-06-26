package com.epm.gestepm.model.project.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.project.dao.entity.creator.ProjectMemberCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectMemberDelete;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.model.project.dao.constants.ProjectMemberQueries.QRY_CREATE_PRM;
import static com.epm.gestepm.model.project.dao.constants.ProjectMemberQueries.QRY_DELETE_PRM;

@AllArgsConstructor
@Component("projectMemberDao")
@EnableExecutionLog(layerMarker = DAO)
public class ProjectMemberDaoImpl implements ProjectMemberDao {

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new project member",
            msgOut = "New project member persisted OK",
            errorMsg = "Failed to persist new project member")
    public void create(final ProjectMemberCreate create) {

        final AttributeMap params = create.collectAttributes();

        final SQLQuery sqlInsert = new SQLQuery()
                .useQuery(QRY_CREATE_PRM)
                .withParams(params);

        this.sqlDatasource.execute(sqlInsert);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for project member",
            msgOut = "Delete for project member persisted OK",
            errorMsg = "Failed to persist delete for project member")
    public void delete(final ProjectMemberDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PRM)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }
}
