package com.epm.gestepm.model.shares.construction.service;

import com.epm.gestepm.lib.audit.AuditProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.construction.dao.ConstructionShareDao;
import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShare;
import com.epm.gestepm.model.shares.construction.dao.entity.filter.ConstructionShareFilter;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSToConstructionShareDto;
import com.epm.gestepm.model.shares.construction.service.mapper.MapCSToConstructionShareFilter;
import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareCreate;
import com.epm.gestepm.model.shares.construction.dao.entity.deleter.ConstructionShareDelete;
import com.epm.gestepm.model.shares.construction.dao.entity.finder.ConstructionShareByIdFinder;
import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareUpdate;
import com.epm.gestepm.model.shares.construction.service.mapper.*;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFilterDto;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareService;
import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareCreateDto;
import com.epm.gestepm.modelapi.shares.construction.dto.deleter.ConstructionShareDeleteDto;
import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareUpdateDto;
import com.epm.gestepm.modelapi.shares.construction.exception.ConstructionShareNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_EDIT_CS;
import static com.epm.gestepm.modelapi.shares.construction.security.ConstructionSharePermission.PRMT_READ_CS;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class ConstructionShareServiceImpl implements ConstructionShareService {

    private final AuditProvider auditProvider;

    private final ConstructionShareDao constructionShareDao;

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "List construction shares")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing construction shares",
            msgOut = "Listing construction shares OK",
            errorMsg = "Failed to list construction shares")
    public List<ConstructionShareDto> list(ConstructionShareFilterDto filterDto) {
        final ConstructionShareFilter filter = getMapper(MapCSToConstructionShareFilter.class).from(filterDto);

        final List<ConstructionShare> list = this.constructionShareDao.list(filter);

        return getMapper(MapCSToConstructionShareDto.class).from(list);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing construction shares",
            msgOut = "Listing construction shares OK",
            errorMsg = "Failed to list construction shares")
    public Page<ConstructionShareDto> list(ConstructionShareFilterDto filterDto, Long offset, Long limit) {

        final ConstructionShareFilter filter = getMapper(MapCSToConstructionShareFilter.class).from(filterDto);

        final Page<ConstructionShare> page = this.constructionShareDao.list(filter, offset, limit);

        return getMapper(MapCSToConstructionShareDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "Find construction share by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding construction share by ID, result can be empty",
            msgOut = "Found construction share by ID",
            errorMsg = "Failed to find construction share by ID")
    public Optional<ConstructionShareDto> find(final ConstructionShareByIdFinderDto finderDto) {
        final ConstructionShareByIdFinder finder = getMapper(MapCSToConstructionShareByIdFinder.class).from(finderDto);

        final Optional<ConstructionShare> found = this.constructionShareDao.find(finder);

        return found.map(getMapper(MapCSToConstructionShareDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_CS, action = "Find construction share by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding construction share by ID, result is expected or will fail",
            msgOut = "Found construction share by ID",
            errorMsg = "Construction share by ID not found")
    public ConstructionShareDto findOrNotFound(final ConstructionShareByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new ConstructionShareNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_CS, action = "Create new construction share")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new construction share",
            msgOut = "New construction share created OK",
            errorMsg = "Failed to create new construction share")
    public ConstructionShareDto create(ConstructionShareCreateDto createDto) {
        final ConstructionShareCreate create = getMapper(MapCSToConstructionShareCreate.class).from(createDto);

        this.auditProvider.auditCreate(create);

        final ConstructionShare result = this.constructionShareDao.create(create);

        return getMapper(MapCSToConstructionShareDto.class).from(result);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_CS, action = "Update construction share")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating construction share",
            msgOut = "Construction share updated OK",
            errorMsg = "Failed to update construction share")
    public ConstructionShareDto update(final ConstructionShareUpdateDto updateDto) {
        final ConstructionShareByIdFinderDto finderDto = new ConstructionShareByIdFinderDto(updateDto.getId());

        final ConstructionShareDto constructionShareDto = findOrNotFound(finderDto);

        final ConstructionShareUpdate update = getMapper(MapCSToConstructionShareUpdate.class).from(updateDto,
                getMapper(MapCSToConstructionShareUpdate.class).from(constructionShareDto));

        if (update.getClosedAt() == null) {
            this.auditProvider.auditClose(update);
        }

        final ConstructionShare updated = this.constructionShareDao.update(update);

        return getMapper(MapCSToConstructionShareDto.class).from(updated);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_CS, action = "Delete construction share")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting construction share",
            msgOut = "Construction share deleted OK",
            errorMsg = "Failed to delete construction share")
    public void delete(ConstructionShareDeleteDto deleteDto) {

        final ConstructionShareByIdFinderDto finderDto = new ConstructionShareByIdFinderDto(deleteDto.getId());

        findOrNotFound(finderDto);

        final ConstructionShareDelete delete = getMapper(MapCSToConstructionShareDelete.class).from(deleteDto);

        this.constructionShareDao.delete(delete);
    }
}
