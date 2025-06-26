package com.epm.gestepm.model.project.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.project.dao.ProjectDao;
import com.epm.gestepm.model.project.dao.entity.Project;
import com.epm.gestepm.model.project.dao.entity.creator.ProjectCreate;
import com.epm.gestepm.model.project.dao.entity.deleter.ProjectDelete;
import com.epm.gestepm.model.project.dao.entity.filter.ProjectFilter;
import com.epm.gestepm.model.project.dao.entity.finder.ProjectByIdFinder;
import com.epm.gestepm.model.project.dao.entity.updater.ProjectUpdate;
import com.epm.gestepm.model.project.service.mapper.*;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.creator.ProjectCreateDto;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectDeleteDto;
import com.epm.gestepm.modelapi.project.dto.filter.ProjectFilterDto;
import com.epm.gestepm.modelapi.project.dto.finder.ProjectByIdFinderDto;
import com.epm.gestepm.modelapi.project.dto.updater.ProjectUpdateDto;
import com.epm.gestepm.modelapi.project.exception.ProjectNotFoundException;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.project.security.ProjectPermission.PRMT_EDIT_PR;
import static com.epm.gestepm.modelapi.project.security.ProjectPermission.PRMT_READ_PR;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao projectDao;

    @Override
    @RequirePermits(value = PRMT_READ_PR, action = "List projects")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing projects",
            msgOut = "Listing projects OK",
            errorMsg = "Failed to list projects")
    public List<ProjectDto> list(ProjectFilterDto filterDto) {
        final ProjectFilter filter = getMapper(MapPRToProjectFilter.class).from(filterDto);

        // this.checkUserRoleAndUpdateFilter(filter);

        final List<Project> list = this.projectDao.list(filter);

        return getMapper(MapPRToProjectDto.class).from(list);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing projects",
            msgOut = "Listing projects OK",
            errorMsg = "Failed to list projects")
    public Page<ProjectDto> list(ProjectFilterDto filterDto, Long offset, Long limit) {
        final ProjectFilter filter = getMapper(MapPRToProjectFilter.class).from(filterDto);

        // this.checkUserRoleAndUpdateFilter(filter);

        final Page<Project> page = this.projectDao.list(filter, offset, limit);

        return getMapper(MapPRToProjectDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PR, action = "Find project by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding project by ID, result can be empty",
            msgOut = "Found project by ID",
            errorMsg = "Failed to find project by ID")
    public Optional<ProjectDto> find(final ProjectByIdFinderDto finderDto) {
        final ProjectByIdFinder finder = getMapper(MapPRToProjectByIdFinder.class).from(finderDto);

        final Optional<Project> found = this.projectDao.find(finder);

        return found.map(getMapper(MapPRToProjectDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PR, action = "Find project by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding project by ID, result is expected or will fail",
            msgOut = "Found project by ID",
            errorMsg = "Project by ID not found")
    public ProjectDto findOrNotFound(final ProjectByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new ProjectNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_PR, action = "Create new project")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new project",
            msgOut = "New project created OK",
            errorMsg = "Failed to create new project")
    public ProjectDto create(ProjectCreateDto createDto) {

        final ProjectCreate create = getMapper(MapPRToProjectCreate.class).from(createDto);

        final Project result = this.projectDao.create(create);

        return getMapper(MapPRToProjectDto.class).from(result);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_PR, action = "Update project")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating project",
            msgOut = "Project updated OK",
            errorMsg = "Failed to update project")
    public ProjectDto update(final ProjectUpdateDto updateDto) {
        final ProjectByIdFinderDto finderDto = new ProjectByIdFinderDto(updateDto.getId());

        findOrNotFound(finderDto);

        final ProjectUpdate update = getMapper(MapPRToProjectUpdate.class).from(updateDto);

        final Project updated = this.projectDao.update(update);

        return getMapper(MapPRToProjectDto.class).from(updated);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Delete project")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting project",
            msgOut = "Project deleted OK",
            errorMsg = "Failed to delete project")
    public void delete(ProjectDeleteDto deleteDto) {

        final ProjectByIdFinderDto finderDto = new ProjectByIdFinderDto(deleteDto.getId());

        findOrNotFound(finderDto);

        final ProjectDelete delete = getMapper(MapPRToProjectDelete.class).from(deleteDto);

        this.projectDao.delete(delete);
    }

    private User getCurrentUser() {
        // TODO: to change
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getDetails();
    }

    private void checkUserRoleAndUpdateFilter(final ProjectFilter filter) {
        final User currentUser = this.getCurrentUser();

        if (!currentUser.getRole().getId().equals(Constants.ROLE_ADMIN_ID) && !currentUser.getRole().getId().equals(Constants.ROLE_TECHNICAL_SUPERVISOR_ID)) {
            filter.setProjectLeaderIds(List.of(currentUser.getId().intValue()));
        }
    }
}
