package com.epm.gestepm.model.project.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.model.project.dao.ProjectMemberDao;
import com.epm.gestepm.model.project.dao.entity.creator.ProjectMemberCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectMemberDelete;
import com.epm.gestepm.model.project.service.mapper.MapPRMToProjectMemberCreate;
import com.epm.gestepm.model.project.service.mapper.MapPRMToProjectMemberDelete;
import com.epm.gestepm.modelapi.project.dto.creator.ProjectMemberCreateDto;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectMemberDeleteDto;
import com.epm.gestepm.modelapi.project.service.ProjectMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.modelapi.project.security.ProjectPermission.PRMT_EDIT_PR;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberDao projectMemberDao;

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_PR, action = "Create new project member")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new project member",
            msgOut = "New project member created OK",
            errorMsg = "Failed to create new project member")
    public void create(ProjectMemberCreateDto createDto) {

        final ProjectMemberCreate create = getMapper(MapPRMToProjectMemberCreate.class).from(createDto);

        this.projectMemberDao.create(create);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Delete project member")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting project member",
            msgOut = "Project member deleted OK",
            errorMsg = "Failed to delete project member")
    public void delete(ProjectMemberDeleteDto deleteDto) {

        final ProjectMemberDelete delete = getMapper(MapPRMToProjectMemberDelete.class).from(deleteDto);

        this.projectMemberDao.delete(delete);
    }
}
