package com.epm.gestepm.model.signings.teleworking.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.signings.checker.SigningUpdateChecker;
import com.epm.gestepm.model.signings.teleworking.dao.TeleworkingSigningDao;
import com.epm.gestepm.model.signings.teleworking.dao.entity.TeleworkingSigning;
import com.epm.gestepm.model.signings.teleworking.dao.entity.creator.TeleworkingSigningCreate;
import com.epm.gestepm.model.signings.teleworking.dao.entity.deleter.TeleworkingSigningDelete;
import com.epm.gestepm.model.signings.teleworking.dao.entity.filter.TeleworkingSigningFilter;
import com.epm.gestepm.model.signings.teleworking.dao.entity.finder.TeleworkingSigningByIdFinder;
import com.epm.gestepm.model.signings.teleworking.dao.entity.updater.TeleworkingSigningUpdate;
import com.epm.gestepm.model.signings.teleworking.service.mapper.*;
import com.epm.gestepm.modelapi.signings.teleworking.dto.TeleworkingSigningDto;
import com.epm.gestepm.modelapi.signings.teleworking.dto.creator.TeleworkingSigningCreateDto;
import com.epm.gestepm.modelapi.signings.teleworking.dto.deleter.TeleworkingSigningDeleteDto;
import com.epm.gestepm.modelapi.signings.teleworking.dto.filter.TeleworkingSigningFilterDto;
import com.epm.gestepm.modelapi.signings.teleworking.dto.finder.TeleworkingSigningByIdFinderDto;
import com.epm.gestepm.modelapi.signings.teleworking.dto.updater.TeleworkingSigningUpdateDto;
import com.epm.gestepm.modelapi.signings.teleworking.exception.TeleworkingSigningNotFoundException;
import com.epm.gestepm.modelapi.signings.teleworking.service.TeleworkingSigningService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.signings.teleworking.security.TeleworkingSigningPermission.PRMT_EDIT_TS;
import static com.epm.gestepm.modelapi.signings.teleworking.security.TeleworkingSigningPermission.PRMT_READ_TS;
import static org.mapstruct.factory.Mappers.getMapper;

@AllArgsConstructor
@Validated
@Service("teleworkingSigningService")
@EnableExecutionLog(layerMarker = SERVICE)
public class TeleworkingSigningServiceImpl implements TeleworkingSigningService {

    private final SigningUpdateChecker signingUpdateChecker;

    private final TeleworkingSigningDao teleworkingSigningDao;

    @Override
    @RequirePermits(value = PRMT_READ_TS, action = "List teleworking signings")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing teleworking signings",
            msgOut = "Listing teleworking signings OK",
            errorMsg = "Failed to list teleworking signings")
    public List<TeleworkingSigningDto> list(TeleworkingSigningFilterDto filterDto) {

        final TeleworkingSigningFilter filter = getMapper(MapTSToTeleworkingSigningFilter.class).from(filterDto);

        final List<TeleworkingSigning> list = this.teleworkingSigningDao.list(filter);

        return getMapper(MapTSToTeleworkingSigningDto.class).from(list);
    }

    @Override
    @RequirePermits(value = PRMT_READ_TS, action = "Page teleworking signings")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating teleworking signings",
            msgOut = "Paginating teleworking signings OK",
            errorMsg = "Failed to paginate teleworking signings")
    public Page<TeleworkingSigningDto> list(TeleworkingSigningFilterDto filterDto, Long offset, Long limit) {

        final TeleworkingSigningFilter filter = getMapper(MapTSToTeleworkingSigningFilter.class).from(filterDto);

        final Page<TeleworkingSigning> page = this.teleworkingSigningDao.list(filter, offset, limit);

        return getMapper(MapTSToTeleworkingSigningDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_TS, action = "Find teleworking signing by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding teleworking signing by ID, result can be empty",
            msgOut = "Found teleworking signing by ID",
            errorMsg = "Failed to find teleworking signing by ID")
    public Optional<TeleworkingSigningDto> find(TeleworkingSigningByIdFinderDto finderDto) {

        final TeleworkingSigningByIdFinder finder = getMapper(MapTSToTeleworkingSigningByIdFinder.class).from(finderDto);

        final Optional<TeleworkingSigning> found = this.teleworkingSigningDao.find(finder);

        return found.map(getMapper(MapTSToTeleworkingSigningDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_TS, action = "Find teleworking signing by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding teleworking signing by ID, result is expected or will fail",
            msgOut = "Found teleworking signing by ID",
            errorMsg = "Personal expense sheet by ID not found")
    public TeleworkingSigningDto findOrNotFound(TeleworkingSigningByIdFinderDto finderDto) {

        final Supplier<RuntimeException> notFound = () -> new TeleworkingSigningNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_TS, action = "Create new teleworking signing")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new teleworking signing",
            msgOut = "New teleworking signing created OK",
            errorMsg = "Failed to create new teleworking signing")
    public TeleworkingSigningDto create(TeleworkingSigningCreateDto createDto) {

        final TeleworkingSigningCreate create = getMapper(MapTSToTeleworkingSigningCreate.class).from(createDto);
        create.setStartedAt(LocalDateTime.now());

        final TeleworkingSigning teleworkingSigning = this.teleworkingSigningDao.create(create);

        return getMapper(MapTSToTeleworkingSigningDto.class).from(teleworkingSigning);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_TS, action = "Update teleworking signing")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating teleworking signing",
            msgOut = "Personal expense sheet updated OK",
            errorMsg = "Failed to update teleworking signing")
    public TeleworkingSigningDto update(TeleworkingSigningUpdateDto updateDto) {

        final TeleworkingSigningByIdFinderDto finderDto = new TeleworkingSigningByIdFinderDto();
        finderDto.setId(updateDto.getId());

        final TeleworkingSigningDto teleworkingSigning = findOrNotFound(finderDto);

        if (updateDto.getClosedLocation() == null) {
            this.signingUpdateChecker.checker(null, teleworkingSigning.getProjectId());
        }

        final TeleworkingSigningUpdate update = getMapper(MapTSToTeleworkingSigningUpdate.class).from(updateDto,
                getMapper(MapTSToTeleworkingSigningUpdate.class).from(teleworkingSigning));

        if (teleworkingSigning.getClosedAt() == null) {
            update.setClosedAt(LocalDateTime.now());
        }

        final TeleworkingSigning updated = this.teleworkingSigningDao.update(update);

        return getMapper(MapTSToTeleworkingSigningDto.class).from(updated);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_TS, action = "Delete teleworking signing")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting teleworking signing",
            msgOut = "Personal expense sheet deleted OK",
            errorMsg = "Failed to delete teleworking signing")
    public void delete(TeleworkingSigningDeleteDto deleteDto) {

        final TeleworkingSigningByIdFinderDto finderDto = new TeleworkingSigningByIdFinderDto();
        finderDto.setId(deleteDto.getId());

        findOrNotFound(finderDto);

        final TeleworkingSigningDelete delete = getMapper(MapTSToTeleworkingSigningDelete.class).from(deleteDto);

        this.teleworkingSigningDao.delete(delete);
    }
}
