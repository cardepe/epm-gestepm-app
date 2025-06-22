package com.epm.gestepm.model.projectmaterial.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.projectmaterial.dao.ProjectMaterialDao;
import com.epm.gestepm.model.projectmaterial.dao.entity.ProjectMaterial;
import com.epm.gestepm.model.projectmaterial.dao.entity.creator.ProjectMaterialCreate;
import com.epm.gestepm.model.projectmaterial.dao.entity.deleter.ProjectMaterialDelete;
import com.epm.gestepm.model.projectmaterial.dao.entity.filter.ProjectMaterialFilter;
import com.epm.gestepm.model.projectmaterial.dao.entity.finder.ProjectMaterialByIdFinder;
import com.epm.gestepm.model.projectmaterial.dao.entity.updater.ProjectMaterialUpdate;
import com.epm.gestepm.model.projectmaterial.service.mapper.*;
import com.epm.gestepm.modelapi.projectmaterial.dto.ProjectMaterialDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.creator.ProjectMaterialCreateDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.deleter.ProjectMaterialDeleteDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.filter.ProjectMaterialFilterDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.finder.ProjectMaterialByIdFinderDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.updater.ProjectMaterialUpdateDto;
import com.epm.gestepm.modelapi.projectmaterial.exception.ProjectMaterialNotFoundException;
import com.epm.gestepm.modelapi.projectmaterial.service.ProjectMaterialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.projectmaterial.security.ProjectMaterialPermission.PRMT_EDIT_PRMAT;
import static com.epm.gestepm.modelapi.projectmaterial.security.ProjectMaterialPermission.PRMT_READ_PRMAT;
import static org.mapstruct.factory.Mappers.getMapper;

@AllArgsConstructor
@Validated
@Service("projectMaterialService")
@EnableExecutionLog(layerMarker = SERVICE)
public class ProjectMaterialServiceImpl implements ProjectMaterialService {

    private final ProjectMaterialDao projectMaterialDao;

    @Override
    @RequirePermits(value = PRMT_READ_PRMAT, action = "List project materials")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing project materials",
            msgOut = "Listing project materials OK",
            errorMsg = "Failed to list project materials")
    public List<ProjectMaterialDto> list(ProjectMaterialFilterDto filterDto) {

        final ProjectMaterialFilter filter = getMapper(MapPRMATToProjectMaterialFilter.class).from(filterDto);

        final List<ProjectMaterial> list = this.projectMaterialDao.list(filter);

        return getMapper(MapPRMATToProjectMaterialDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PRMAT, action = "Page project materials")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating project materials",
            msgOut = "Paginating project materials OK",
            errorMsg = "Failed to paginate project materials")
    public Page<ProjectMaterialDto> list(ProjectMaterialFilterDto filterDto, Long offset, Long limit) {

        final ProjectMaterialFilter filter = getMapper(MapPRMATToProjectMaterialFilter.class).from(filterDto);

        final Page<ProjectMaterial> page = this.projectMaterialDao.list(filter, offset, limit);

        return getMapper(MapPRMATToProjectMaterialDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PRMAT, action = "Find project material by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding project material by ID, result can be empty",
            msgOut = "Found project material by ID",
            errorMsg = "Failed to find project material by ID")
    public Optional<ProjectMaterialDto> find(ProjectMaterialByIdFinderDto finderDto) {

        final ProjectMaterialByIdFinder finder = getMapper(MapPRMATToProjectMaterialByIdFinder.class).from(finderDto);

        final Optional<ProjectMaterial> found = this.projectMaterialDao.find(finder);

        return found.map(getMapper(MapPRMATToProjectMaterialDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_PRMAT, action = "Find project material by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding project material by ID, result is expected or will fail",
            msgOut = "Found project material by ID",
            errorMsg = "Personal expense sheet by ID not found")
    public ProjectMaterialDto findOrNotFound(ProjectMaterialByIdFinderDto finderDto) {

        final Supplier<RuntimeException> notFound = () -> new ProjectMaterialNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PRMAT, action = "Create new project material")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new project material",
            msgOut = "New project material created OK",
            errorMsg = "Failed to create new project material")
    public ProjectMaterialDto create(ProjectMaterialCreateDto createDto) {

        final ProjectMaterialCreate create = getMapper(MapPRMATToProjectMaterialCreate.class).from(createDto);

        final ProjectMaterial projectMaterial = this.projectMaterialDao.create(create);

        return getMapper(MapPRMATToProjectMaterialDto.class).from(projectMaterial);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PRMAT, action = "Update project material")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating project material",
            msgOut = "Personal expense sheet updated OK",
            errorMsg = "Failed to update project material")
    public ProjectMaterialDto update(ProjectMaterialUpdateDto updateDto) {

        final ProjectMaterialByIdFinderDto finderDto = new ProjectMaterialByIdFinderDto();
        finderDto.setId(updateDto.getId());

        findOrNotFound(finderDto);

        final ProjectMaterialUpdate update = getMapper(MapPRMATToProjectMaterialUpdate.class).from(updateDto);

        final ProjectMaterial updated = this.projectMaterialDao.update(update);

        return getMapper(MapPRMATToProjectMaterialDto.class).from(updated);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PRMAT, action = "Delete project material")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting project material",
            msgOut = "Personal expense sheet deleted OK",
            errorMsg = "Failed to delete project material")
    public void delete(ProjectMaterialDeleteDto deleteDto) {

        final ProjectMaterialByIdFinderDto finderDto = new ProjectMaterialByIdFinderDto();
        finderDto.setId(deleteDto.getId());

        findOrNotFound(finderDto);

        final ProjectMaterialDelete delete = getMapper(MapPRMATToProjectMaterialDelete.class).from(deleteDto);

        this.projectMaterialDao.delete(delete);
    }
}
