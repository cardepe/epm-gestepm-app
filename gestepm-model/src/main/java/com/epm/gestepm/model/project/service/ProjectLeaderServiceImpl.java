package com.epm.gestepm.model.project.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.model.project.dao.ProjectLeaderDao;
import com.epm.gestepm.model.project.dao.entity.creator.ProjectLeaderCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectLeaderDelete;
import com.epm.gestepm.model.project.service.mapper.*;
import com.epm.gestepm.modelapi.project.dto.creator.ProjectLeaderCreateDto;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectLeaderDeleteDto;
import com.epm.gestepm.modelapi.project.service.ProjectLeaderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.project.security.ProjectPermission.PRMT_EDIT_PR;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class ProjectLeaderServiceImpl implements ProjectLeaderService {

    private final ProjectLeaderDao projectLeaderDao;

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_PR, action = "Create new project leader")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new project leader",
            msgOut = "New project leader created OK",
            errorMsg = "Failed to create new project leader")
    public void create(ProjectLeaderCreateDto createDto) {

        final ProjectLeaderCreate create = getMapper(MapPRLToProjectLeaderCreate.class).from(createDto);

        this.projectLeaderDao.create(create);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Delete project leader")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting project leader",
            msgOut = "Project leader deleted OK",
            errorMsg = "Failed to delete project leader")
    public void delete(ProjectLeaderDeleteDto deleteDto) {

        final ProjectLeaderDelete delete = getMapper(MapPRLToProjectLeaderDelete.class).from(deleteDto);

        this.projectLeaderDao.delete(delete);
    }
}
