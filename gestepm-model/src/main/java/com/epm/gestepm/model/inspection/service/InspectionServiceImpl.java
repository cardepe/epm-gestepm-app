package com.epm.gestepm.model.inspection.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.inspection.dao.InspectionDao;
import com.epm.gestepm.model.inspection.dao.entity.Inspection;
import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionCreate;
import com.epm.gestepm.model.inspection.dao.entity.deleter.InspectionDelete;
import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFilter;
import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionByIdFinder;
import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionUpdate;
import com.epm.gestepm.model.inspection.service.mapper.*;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.deleter.InspectionDeleteDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFilterDto;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionByIdFinderDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import com.epm.gestepm.modelapi.inspection.exception.InspectionNotFoundException;
import com.epm.gestepm.modelapi.inspection.service.InspectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_EDIT_I;
import static com.epm.gestepm.modelapi.inspection.security.InspectionPermission.PRMT_READ_I;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class InspectionServiceImpl implements InspectionService {

    private final InspectionDao inspectionDao;

    public InspectionServiceImpl(InspectionDao inspectionDao) {
        this.inspectionDao = inspectionDao;
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "List inspections")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating inspections",
            msgOut = "Paginating inspections OK",
            errorMsg = "Failed to paginate inspections")
    public Page<InspectionDto> list(InspectionFilterDto filterDto, Long offset, Long limit) {
        final InspectionFilter filter = getMapper(MapIToInspectionFilter.class).from(filterDto);

        final Page<Inspection> page = this.inspectionDao.list(filter, offset, limit);

        return getMapper(MapIToInspectionDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Find inspection by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding inspection by ID, result can be empty",
            msgOut = "Found inspection by ID",
            errorMsg = "Failed to find inspection by ID")
    public Optional<InspectionDto> find(final InspectionByIdFinderDto finderDto) {
        final InspectionByIdFinder finder = getMapper(MapIToInspectionByIdFinder.class).from(finderDto);

        final Optional<Inspection> found = this.inspectionDao.find(finder);

        return found.map(getMapper(MapIToInspectionDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_I, action = "Find inspection by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding inspection by ID, result is expected or will fail",
            msgOut = "Found inspection by ID",
            errorMsg = "Intervention by ID not found")
    public InspectionDto findOrNotFound(final InspectionByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new InspectionNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_I, action = "Create new inspection")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new inspection",
            msgOut = "New country inspection OK",
            errorMsg = "Failed to create new inspection")
    public InspectionDto create(InspectionCreateDto createDto) {
        
        final InspectionCreate create = getMapper(MapIToInspectionCreate.class).from(createDto);
        create.setStartDate(OffsetDateTime.now());

        final Inspection result = this.inspectionDao.create(create);

        return getMapper(MapIToInspectionDto.class).from(result);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Update inspection")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating inspection",
            msgOut = "Intervention updated OK",
            errorMsg = "Failed to update inspection")
    public InspectionDto update(InspectionUpdateDto updateDto) {

        final InspectionByIdFinderDto finderDto = new InspectionByIdFinderDto();
        finderDto.setId(updateDto.getId());

        final InspectionDto InspectionDto = findOrNotFound(finderDto);

        final InspectionUpdate update = getMapper(MapIToInspectionUpdate.class).from(updateDto,
                getMapper(MapIToInspectionUpdate.class).from(InspectionDto));

        final Inspection updated = this.inspectionDao.update(update);

        return getMapper(MapIToInspectionDto.class).from(updated);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_I, action = "Delete inspection")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting inspection",
            msgOut = "Intervention deleted OK",
            errorMsg = "Failed to delete inspection")
    public void delete(InspectionDeleteDto deleteDto) {

        final InspectionByIdFinderDto finderDto = new InspectionByIdFinderDto();
        finderDto.setId(deleteDto.getId());

        findOrNotFound(finderDto);

        final InspectionDelete delete = getMapper(MapIToInspectionDelete.class).from(deleteDto);

        this.inspectionDao.delete(delete);
    }
}
