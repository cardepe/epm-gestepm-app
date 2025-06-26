package com.epm.gestepm.model.project.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLInsert;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.project.dao.entity.creator.ProjectResponsibleCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectResponsibleDelete;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.project.dao.constants.ProjectResponsibleQueries.QRY_CREATE_PRR;
import static com.epm.gestepm.model.project.dao.constants.ProjectResponsibleQueries.QRY_DELETE_PRR;

@AllArgsConstructor
@Component("projectResponsibleDao")
@EnableExecutionLog(layerMarker = DAO)
public class ProjectResponsibleDaoImpl implements ProjectResponsibleDao {

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new project responsible",
            msgOut = "New project responsible persisted OK",
            errorMsg = "Failed to persist new project responsible")
    public void create(final ProjectResponsibleCreate create) {

        final AttributeMap params = create.collectAttributes();

        final SQLQuery sqlInsert = new SQLQuery()
                .useQuery(QRY_CREATE_PRR)
                .withParams(params);

        this.sqlDatasource.execute(sqlInsert);
    }

    @Override
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Persisting delete for project responsible",
            msgOut = "Delete for project responsible persisted OK",
            errorMsg = "Failed to persist delete for project responsible")
    public void delete(final ProjectResponsibleDelete delete) {

        final AttributeMap params = delete.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_DELETE_PRR)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);
    }
}
