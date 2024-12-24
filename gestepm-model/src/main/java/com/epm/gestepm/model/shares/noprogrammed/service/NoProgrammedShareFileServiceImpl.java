package com.epm.gestepm.model.shares.noprogrammed.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.model.shares.noprogrammed.dao.NoProgrammedShareFileDao;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShareFile;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareFileCreate;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFileFilter;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareFileByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSFToNoProgrammedShareFileByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSFToNoProgrammedShareFileCreate;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSFToNoProgrammedShareFileDto;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSFToNoProgrammedShareFileFilter;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareFileDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareFileCreateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareFileByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareFileNotFoundException;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_READ;
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_EDIT_NPS;
import static com.epm.gestepm.modelapi.shares.noprogrammed.security.NoProgrammedSharePermission.PRMT_READ_NPS;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class NoProgrammedShareFileServiceImpl implements NoProgrammedShareFileService {
    
    private final NoProgrammedShareFileDao noProgrammedShareDao;

    public NoProgrammedShareFileServiceImpl(NoProgrammedShareFileDao noProgrammedShareDao) {
        this.noProgrammedShareDao = noProgrammedShareDao;
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "List countries")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Paginating no programmed share file files",
            msgOut = "Paginating no programmed share file files OK",
            errorMsg = "Failed to paginate no programmed share file files")
    public List<NoProgrammedShareFileDto> list(NoProgrammedShareFileFilterDto filterDto) {
        final NoProgrammedShareFileFilter filter = getMapper(MapNPSFToNoProgrammedShareFileFilter.class).from(filterDto);

        final List<NoProgrammedShareFile> page = this.noProgrammedShareDao.list(filter);

        return getMapper(MapNPSFToNoProgrammedShareFileDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "Find no programmed share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding no programmed share file by ID, result can be empty",
            msgOut = "Found no programmed share file by ID",
            errorMsg = "Failed to find no programmed share file by ID")
    public Optional<NoProgrammedShareFileDto> find(final NoProgrammedShareFileByIdFinderDto finderDto) {
        final NoProgrammedShareFileByIdFinder finder = getMapper(MapNPSFToNoProgrammedShareFileByIdFinder.class).from(finderDto);

        final Optional<NoProgrammedShareFile> found = this.noProgrammedShareDao.find(finder);

        return found.map(getMapper(MapNPSFToNoProgrammedShareFileDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_NPS, action = "Find no programmed share file by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding no programmed share file by ID, result is expected or will fail",
            msgOut = "Found no programmed share file by ID",
            errorMsg = "No programmed share by ID not found")
    public NoProgrammedShareFileDto findOrNotFound(final NoProgrammedShareFileByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new NoProgrammedShareFileNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_NPS, action = "Create new no programmed share file")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new no programmed share file",
            msgOut = "New country no programmed share file OK",
            errorMsg = "Failed to create new no programmed share file")
    public NoProgrammedShareFileDto create(NoProgrammedShareFileCreateDto createDto) {
        final NoProgrammedShareFileCreate create = getMapper(MapNPSFToNoProgrammedShareFileCreate.class).from(createDto);

        final NoProgrammedShareFile result = this.noProgrammedShareDao.create(create);

        return getMapper(MapNPSFToNoProgrammedShareFileDto.class).from(result);
    }

}
