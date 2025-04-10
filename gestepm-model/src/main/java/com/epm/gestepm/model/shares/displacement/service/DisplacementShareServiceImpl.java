package com.epm.gestepm.model.shares.displacement.service;

import com.epm.gestepm.lib.audit.AuditProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.displacement.dao.DisplacementShareDao;
import com.epm.gestepm.model.shares.displacement.dao.entity.DisplacementShare;
import com.epm.gestepm.model.shares.displacement.dao.entity.creator.DisplacementShareCreate;
import com.epm.gestepm.model.shares.displacement.dao.entity.deleter.DisplacementShareDelete;
import com.epm.gestepm.model.shares.displacement.dao.entity.filter.DisplacementShareFilter;
import com.epm.gestepm.model.shares.displacement.dao.entity.finder.DisplacementShareByIdFinder;
import com.epm.gestepm.model.shares.displacement.dao.entity.updater.DisplacementShareUpdate;
import com.epm.gestepm.model.shares.displacement.service.mapper.*;
import com.epm.gestepm.modelapi.shares.displacement.dto.DisplacementShareDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.creator.DisplacementShareCreateDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.deleter.DisplacementShareDeleteDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.filter.DisplacementShareFilterDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.finder.DisplacementShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.updater.DisplacementShareUpdateDto;
import com.epm.gestepm.modelapi.shares.displacement.exception.DisplacementShareNotFoundException;
import com.epm.gestepm.modelapi.shares.displacement.service.DisplacementShareService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.shares.displacement.security.DisplacementSharePermission.PRMT_EDIT_DI;
import static com.epm.gestepm.modelapi.shares.displacement.security.DisplacementSharePermission.PRMT_READ_DI;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class DisplacementShareServiceImpl implements DisplacementShareService {

    private final AuditProvider auditProvider;

    private final DisplacementShareDao displacementShareDao;

    @Override
    @RequirePermits(value = PRMT_READ_DI, action = "List displacement shares")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing displacement shares",
            msgOut = "Listing displacement shares OK",
            errorMsg = "Failed to list displacement shares")
    public List<DisplacementShareDto> list(DisplacementShareFilterDto filterDto) {
        final DisplacementShareFilter filter = getMapper(MapDIToDisplacementShareFilter.class).from(filterDto);

        final List<DisplacementShare> list = this.displacementShareDao.list(filter);

        return getMapper(MapDIToDisplacementShareDto.class).from(list);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing displacement shares",
            msgOut = "Listing displacement shares OK",
            errorMsg = "Failed to list displacement shares")
    public Page<DisplacementShareDto> list(DisplacementShareFilterDto filterDto, Long offset, Long limit) {

        final DisplacementShareFilter filter = getMapper(MapDIToDisplacementShareFilter.class).from(filterDto);

        final Page<DisplacementShare> page = this.displacementShareDao.list(filter, offset, limit);

        return getMapper(MapDIToDisplacementShareDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_DI, action = "Find displacement share by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding displacement share by ID, result can be empty",
            msgOut = "Found displacement share by ID",
            errorMsg = "Failed to find displacement share by ID")
    public Optional<DisplacementShareDto> find(final DisplacementShareByIdFinderDto finderDto) {
        final DisplacementShareByIdFinder finder = getMapper(MapDIToDisplacementShareByIdFinder.class).from(finderDto);

        final Optional<DisplacementShare> found = this.displacementShareDao.find(finder);

        return found.map(getMapper(MapDIToDisplacementShareDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_DI, action = "Find displacement share by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding displacement share by ID, result is expected or will fail",
            msgOut = "Found displacement share by ID",
            errorMsg = "Displacement share by ID not found")
    public DisplacementShareDto findOrNotFound(final DisplacementShareByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new DisplacementShareNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_DI, action = "Create new displacement share")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new displacement share",
            msgOut = "New displacement share created OK",
            errorMsg = "Failed to create new displacement share")
    public DisplacementShareDto create(DisplacementShareCreateDto createDto) {
        final DisplacementShareCreate create = getMapper(MapDIToDisplacementShareCreate.class).from(createDto);

        this.auditProvider.auditCreate(create);

        final DisplacementShare result = this.displacementShareDao.create(create);

        return getMapper(MapDIToDisplacementShareDto.class).from(result);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_DI, action = "Update displacement share")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating displacement share",
            msgOut = "Displacement share updated OK",
            errorMsg = "Failed to update displacement share")
    public DisplacementShareDto update(final DisplacementShareUpdateDto updateDto) {
        final DisplacementShareUpdate update = getMapper(MapDIToDisplacementShareUpdate.class).from(updateDto);

        this.auditProvider.auditUpdate(update);

        final DisplacementShare updated = this.displacementShareDao.update(update);

        return getMapper(MapDIToDisplacementShareDto.class).from(updated);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_DI, action = "Delete displacement share")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting displacement share",
            msgOut = "Displacement share deleted OK",
            errorMsg = "Failed to delete displacement share")
    public void delete(DisplacementShareDeleteDto deleteDto) {

        final DisplacementShareByIdFinderDto finderDto = new DisplacementShareByIdFinderDto(deleteDto.getId());

        findOrNotFound(finderDto);

        final DisplacementShareDelete delete = getMapper(MapDIToDisplacementShareDelete.class).from(deleteDto);

        this.displacementShareDao.delete(delete);
    }
}
